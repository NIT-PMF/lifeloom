package nit.school.lifeloom.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var anyChartView: AnyChartView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        anyChartView = binding.anyChartView

        val cartesian: Cartesian = AnyChart.column()

        setupGraph(cartesian)

        return binding.root
    }

    private fun setupGraph(cartesian: Cartesian) {
        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Rouge", 80540))
        data.add(ValueDataEntry("Foundation", 94190))
        data.add(ValueDataEntry("Mascara", 102610))
        data.add(ValueDataEntry("Lip gloss", 110430))
        data.add(ValueDataEntry("Lipstick", 128000))
        data.add(ValueDataEntry("Nail polish", 143760))
        data.add(ValueDataEntry("Eyebrow pencil", 170670))
        data.add(ValueDataEntry("Eyeliner", 213210))
        data.add(ValueDataEntry("Eyeshadows", 249980))

        val column: Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title("Top 10 Cosmetic Products by Revenue")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Product")
        cartesian.yAxis(0).title("Revenue")

        anyChartView.setChart(cartesian)
    }
}
