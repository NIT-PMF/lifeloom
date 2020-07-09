package nit.school.lifeloom.ui.chartDisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.SingleValueDataSet
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.CircularGauge
import com.anychart.charts.Waterfall
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentChartDisplayBinding
import nit.school.lifeloom.singleton.incrementSingleton
import nit.school.lifeloom.singleton.quantitySingleton
import nit.school.lifeloom.singleton.timePeriodSingleton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChartDisplayFragment : Fragment() {

    private lateinit var binding: FragmentChartDisplayBinding
    private lateinit var anyChartView: AnyChartView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart_display, container, false)
        val categoryName = arguments?.getString("category_name") ?: "Unknown"
        val activityType = arguments?.getString("activity_type")?.capitalize() ?: "Unknown"
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = categoryName
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = activityType
        binding.textChartName.text = categoryName

        anyChartView = binding.anyChartView

        val cartesian: Cartesian = AnyChart.column()

        getDataAndDisplay(activityType, categoryName, cartesian)

        return binding.root
    }

    private fun getDataAndDisplay(type: String, categoryName: String, cartesian: Cartesian) {
        val chartData: MutableList<DataEntry> = ArrayList()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        if (type == "Increment") {
            val listIncrement = incrementSingleton.getActivities().filter { it?.name == categoryName }.take(10)

            for (item in listIncrement) {
                chartData.add(ValueDataEntry((formatter.format(item?.date!!.time)), item.value))
            }
            setupGraph(cartesian, chartData, listIncrement[0]!!.increment)
        } else if (type == "Quantity") {
            val quantitySorted = quantitySingleton.getActivities().filter{it?.name == categoryName}.sortedByDescending { it?.value }
            val quantityIncrement = quantitySingleton.getActivities().filter { it?.name == categoryName }.take(1)
            setupSpeedGraph(cartesian, categoryName, quantityIncrement[0]?.value ?: 0, quantitySorted[quantitySorted.lastIndex]?.value ?: 0, quantitySorted[0]?.value ?: 100)

        } else {
            val timeIncrement = timePeriodSingleton.getActivities().filter { it?.name == categoryName }.take(10)
            for (item in timeIncrement) {

                chartData.add(ValueDataEntry(formatter.format(item?.date!!.time), item?.value / 1000))
            }
            setupWaterfallGraph(chartData)
        }
    }


    private fun setupGraph(cartesian: Cartesian, data: MutableList<DataEntry>, value: Int) {

        val column: Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title("Last 10 days of activity")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("{%Value}")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Days")
        cartesian.yAxis(0).title("Increment by $value")

        anyChartView.setChart(cartesian)
    }
    private fun setupSpeedGraph(cartesian: Cartesian, categoryName: String, value: Int, smallest: Int, largest: Int) {

        val circularGauge: CircularGauge = AnyChart.circular()
        circularGauge.fill("#fff")
            .stroke(null)
            .padding(0, 0, 0, 0)
            .margin(30, 30, 30, 30)
        circularGauge.startAngle(0)
            .sweepAngle(360)

        val currentValue = value
        circularGauge.data(SingleValueDataSet(arrayOf(currentValue)))

        circularGauge.axis(0)
            .startAngle(-150)
            .radius(80)
            .sweepAngle(300)
            .width(3)
            .ticks("{ type: 'line', length: 4, position: 'outside' }")

        circularGauge.axis(0).labels().position("outside")

        circularGauge.axis(0).scale()
            .minimum(0)
            .maximum(largest)

        circularGauge.axis(0).scale()
            .ticks("{interval: 10}")
            .minorTicks("{interval: 10}")

        circularGauge.needle(0)
            .stroke(null)
            .startRadius("6%")
            .endRadius("38%")
            .startWidth("2%")
            .endWidth(0)

        circularGauge.cap()
            .radius("4%")
            .enabled(true)
            .stroke(null)

        circularGauge.label(0)
            .text("<span style=\"font-size: 14\">$categoryName</span>")
            .useHtml(true)
        circularGauge.label(0)
            .anchor(Anchor.CENTER_TOP)
            .offsetY(100)
            .padding(15, 20, 0, 0)

        circularGauge.label(1)
            .text("<span style=\"font-size: 20\">$currentValue</span>")
            .useHtml(true)
        circularGauge.label(1)
            .anchor(Anchor.CENTER_TOP)
            .offsetY(-100)
            .padding(5, 10, 0, 0)
            .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}")

        circularGauge.range(
            0,
            """{
            from: 0,
            to: 25,
            position: 'inside',
            fill: 'green 0.5',
            stroke: '1 #000',
            startSize: 6,
            endSize: 6,
            radius: 80,
            zIndex: 1
          }"""
        )

        circularGauge.range(
            1,
            """{
            from: $smallest,
            to: $largest,
            position: 'inside',
            fill: 'red 0.5',
            stroke: '1 #000',
            startSize: 6,
            endSize: 6,
            radius: 80,
            zIndex: 1
          }"""
        )

        anyChartView.setChart(circularGauge)
    }

    private fun setupWaterfallGraph(data: MutableList<DataEntry>) {
        val waterfall: Waterfall = AnyChart.waterfall()

        waterfall.title("Last 10 Days in Time Period")

        waterfall.yScale().minimum(0.0)

        waterfall.yAxis(0).labels().format("{%Value}{scale:(1000)(1)|(s)}")
        waterfall.labels().enabled(true)
        waterfall.labels().format(
            """function() {
              if (this['isTotal']) {
                return anychart.format.number(this.absolute, {
                  scale: true
                })
              }
        
              return anychart.format.number(this.value, {
                scale: true
              })
            }"""
        )

        val end = DataEntry()
        end.setValue("x", "End")
        end.setValue("isTotal", true)
        data.add(end)

        waterfall.data(data)

        anyChartView.setChart(waterfall)
    }

}
