package com.example.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

val MintBackground = Color(0xFFF4FAF8)
val CardWhite = Color.White
val Lavender = Color(0xFFC5B4E3)
val DarkMintGreen = Color(0xFF6A9B93)
val GrayText = Color(0xFF8E8E93)
val TooltipBlack = Color(0xFF1C1C1E)
val ChartPink = Color(0xFFF5A3B9)
val LightChartPink = Color(0xFFFCE4EC)
val SoftMint = Color(0xFFD0E7E2)
val LightGray = Color(0xFFF2F2F7)
val SuccessGreen = Color(0xFF4CAF50)
val DarkText = Color(0xFF1C1C1E)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                InsightsScreen()
            }
        }
    }
}

@Composable
fun InsightsScreen() {
    var selectedTab by remember { mutableStateOf("Insights") }
    var weightPeriod by remember { mutableStateOf("Monthly") }
    var cyclePeriod by remember { mutableStateOf("Jan - Jun") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MintBackground)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFFFEBF0), Color.Transparent),
                        center = Offset(size.width * 0.9f, size.height * 0.1f),
                        radius = size.width * 1.2f
                    ),
                    center = Offset(size.width * 0.9f, size.height * 0.1f),
                    radius = size.width * 1.2f
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 120.dp)
        ) {
            HeaderSection()

            SectionTitle("Stability Summary")
            StabilitySummaryCard()

            SectionTitle("Cycle Trends")
            CycleTrendsCard(
                currentPeriod = cyclePeriod,
                onPrev = { cyclePeriod = "Jul - Dec 23" },
                onNext = { cyclePeriod = "Jan - Jun 24" }
            )

            SectionTitle("Body & Metabolic Trends")
            WeightTrendCard(
                selectedPeriod = weightPeriod,
                onPeriodSelected = { weightPeriod = it }
            )

            SectionTitle("Body Signals")
            SymptomTrendsCard()

            SectionTitle("Lifestyle Impact")
            LifestyleImpactCard()

            Spacer(modifier = Modifier.height(40.dp))
        }

        BottomNavBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.size(18.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                Dot(Lavender)
                Dot(Lavender.copy(alpha = 0.5f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                Dot(Lavender.copy(alpha = 0.5f))
                Dot(Lavender)
            }
        }

        Text(
            text = "Insights",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun Dot(color: Color) {
    Box(
        modifier = Modifier
            .size(7.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = DarkText,
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 22.dp, bottom = 8.dp)
    )
}

@Composable
fun DashboardCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        content()
    }
}

@Composable
fun StabilitySummaryCard() {
    DashboardCard {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Based on your recent logs and symptom patterns.",
                fontSize = 14.sp,
                color = GrayText,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "Stability Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "78", fontSize = 42.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Text(text = "%", fontSize = 20.sp, color = DarkText, modifier = Modifier.padding(bottom = 8.dp, start = 2.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                StabilityChart()
            }
        }
    }
}

@Composable
fun StabilityChart() {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(color = GrayText, fontSize = 11.sp)
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val padding = 30.dp.toPx()
        val chartWidth = width - padding * 2
        val chartHeight = height - padding * 2
        
        // Y-axis labels
        val yLabels = listOf("32d", "28d", "24d")
        yLabels.forEachIndexed { index, label ->
            val y = padding + (chartHeight / (yLabels.size - 1)) * index
            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = labelStyle,
                topLeft = Offset(0f, y - 8.dp.toPx())
            )
        }
        
        // Data points (normalized)
        val pointsDark = listOf(
            Offset(0f, 1f),
            Offset(0.33f, 0.9f),
            Offset(0.66f, 0.7f),
            Offset(1f, 0.6f)
        )
        
        val pointsLight = listOf(
            Offset(0f, 1f),
            Offset(0.33f, 0.85f),
            Offset(0.66f, 0.6f),
            Offset(1f, 0.4f)
        )
        
        fun drawShadedArea(points: List<Offset>, color: Color) {
            val path = Path()
            val startX = padding + points.first().x * chartWidth
            val endX = padding + points.last().x * chartWidth
            
            path.moveTo(startX, chartHeight + padding)
            points.forEach { point ->
                val x = padding + point.x * chartWidth
                val y = padding + point.y * chartHeight
                path.lineTo(x, y)
            }
            path.lineTo(endX, chartHeight + padding)
            path.close()
            drawPath(path = path, color = color)
        }
        
        // Draw shaded areas
        drawShadedArea(pointsLight, Lavender.copy(alpha = 0.2f))
        drawShadedArea(pointsDark, Lavender.copy(alpha = 0.5f))
        
        // X-axis labels
        val xLabels = listOf("Jan", "Feb", "Mar", "Apr")
        xLabels.forEachIndexed { index, label ->
            val x = padding + (chartWidth / (xLabels.size - 1)) * index
            val isSelected = index == 2 // March
            
            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = if (isSelected) labelStyle.copy(color = DarkText, fontWeight = FontWeight.Bold) else labelStyle,
                topLeft = Offset(x - 10.dp.toPx(), height - 15.dp.toPx())
            )
            
            if (isSelected) {
                val marchX = x
                val marchY = padding + pointsDark[index].y * chartHeight
                
                // Dotted vertical line
                drawLine(
                    color = SuccessGreen.copy(alpha = 0.5f),
                    start = Offset(marchX, padding),
                    end = Offset(marchX, chartHeight + padding),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                
                // Green dot
                drawCircle(
                    color = SuccessGreen.copy(alpha = 0.8f),
                    radius = 6.dp.toPx(),
                    center = Offset(marchX, marchY)
                )
                
                // Tooltip
                val tooltipText = "Stability\nImproving"
                val textLayoutResult = textMeasurer.measure(
                    text = AnnotatedString(tooltipText),
                    style = TextStyle(color = Color.White, fontSize = 11.sp, textAlign = TextAlign.Center)
                )
                val tooltipWidth = textLayoutResult.size.width + 24.dp.toPx()
                val tooltipHeight = textLayoutResult.size.height + 16.dp.toPx()
                val tooltipX = marchX - tooltipWidth / 2
                val tooltipY = marchY - tooltipHeight - 12.dp.toPx()
                
                drawRoundRect(
                    color = TooltipBlack,
                    topLeft = Offset(tooltipX, tooltipY),
                    size = Size(tooltipWidth, tooltipHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
                
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(tooltipX + 12.dp.toPx(), tooltipY + 8.dp.toPx())
                )
                
                // Triangle pointer
                val trianglePath = Path().apply {
                    moveTo(marchX - 6.dp.toPx(), tooltipY + tooltipHeight)
                    lineTo(marchX + 6.dp.toPx(), tooltipY + tooltipHeight)
                    lineTo(marchX, tooltipY + tooltipHeight + 6.dp.toPx())
                    close()
                }
                drawPath(path = trianglePath, color = TooltipBlack)
            }
        }
    }
}

@Composable
fun CycleTrendsCard(
    currentPeriod: String,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    DashboardCard {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPrev,
                    modifier = Modifier.size(32.dp).border(1.dp, GrayText.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev", tint = GrayText, modifier = Modifier.size(20.dp))
                }
                
                Text(text = currentPeriod, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = DarkText)
                
                IconButton(
                    onClick = onNext,
                    modifier = Modifier.size(32.dp).border(1.dp, GrayText.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", tint = GrayText, modifier = Modifier.size(20.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                CycleBarChart()
            }
        }
    }
}

@Composable
fun CycleBarChart() {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(color = GrayText, fontSize = 10.sp)
    val valueStyle = TextStyle(color = DarkText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    
    val data = listOf(
        BarData("Jan", 28),
        BarData("Feb", 30),
        BarData("Mar", 28),
        BarData("Apr", 32),
        BarData("May", 28),
        BarData("Jun", 28)
    )
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val barWidth = 14.dp.toPx()
        val spacing = (width - (barWidth * data.size)) / (data.size + 1)
        val maxVal = 40f
        val chartHeight = height - 40.dp.toPx()
        
        data.forEachIndexed { index, bar ->
            val x = spacing + index * (barWidth + spacing)
            val barHeight = (bar.value / maxVal) * chartHeight
            val y = chartHeight - barHeight + 10.dp.toPx()
            
            // Draw bar background (Lavender base)
            drawRoundRect(
                color = Lavender.copy(alpha = 0.3f),
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth / 2, barWidth / 2)
            )
            
            // Draw pink lower segment (approx 1/3)
            val pinkHeight = barHeight * 0.4f
            drawRoundRect(
                color = ChartPink,
                topLeft = Offset(x, y + barHeight - pinkHeight),
                size = Size(barWidth, pinkHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth / 2, barWidth / 2)
            )
            
            // Draw green middle marker
            val markerY = y + barHeight * 0.5f
            drawCircle(
                color = SuccessGreen,
                radius = 4.dp.toPx(),
                center = Offset(x + barWidth / 2, markerY)
            )
            
            // Value text
            drawText(
                textMeasurer = textMeasurer,
                text = bar.value.toString(),
                style = valueStyle,
                topLeft = Offset(x + barWidth / 2 - 8.dp.toPx(), y - 20.dp.toPx())
            )
            
            // Label text
            drawText(
                textMeasurer = textMeasurer,
                text = bar.label,
                style = labelStyle,
                topLeft = Offset(x + barWidth / 2 - 10.dp.toPx(), chartHeight + 15.dp.toPx())
            )
        }
    }
}

data class BarData(val label: String, val value: Int)

@Composable
fun WeightTrendCard(
    selectedPeriod: String,
    onPeriodSelected: (String) -> Unit
) {
    DashboardCard {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(text = "Your weight", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = DarkText)
                    Text(text = "in kg", fontSize = 12.sp, color = GrayText)
                }
                
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(LightGray)
                        .padding(4.dp)
                ) {
                    PeriodButton("Monthly", selectedPeriod == "Monthly") { onPeriodSelected("Monthly") }
                    PeriodButton("Weekly", selectedPeriod == "Weekly") { onPeriodSelected("Weekly") }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            val monthlyPoints = listOf(0.4f, 0.35f, 0.5f, 0.45f, 0.3f)
            val monthlyLabels = listOf("Jan", "Feb", "Mar", "Apr", "May")
            
            val weeklyPoints = listOf(0.5f, 0.48f, 0.52f, 0.45f, 0.47f, 0.42f, 0.43f)
            val weeklyLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                WeightSmoothLineChart(
                    dataPoints = if (selectedPeriod == "Monthly") monthlyPoints else weeklyPoints,
                    xLabels = if (selectedPeriod == "Monthly") monthlyLabels else weeklyLabels
                )
            }
        }
    }
}

@Composable
fun PeriodButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) DarkText else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else GrayText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun WeightSmoothLineChart(
    dataPoints: List<Float>,
    xLabels: List<String>
) {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(color = GrayText, fontSize = 10.sp)
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val paddingX = 30.dp.toPx()
        val paddingY = 20.dp.toPx()
        val chartWidth = width - paddingX * 2
        val chartHeight = height - paddingY * 2
        
        // Y-axis labels
        val yLabels = listOf("75", "50", "25")
        yLabels.forEachIndexed { index, label ->
            val y = paddingY + (chartHeight / (yLabels.size - 1)) * index
            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = labelStyle,
                topLeft = Offset(0f, y - 8.dp.toPx())
            )
            drawLine(
                color = GrayText.copy(alpha = 0.1f),
                start = Offset(paddingX, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }
        
        // Data points (normalized 0.0 to 1.0)
        val xStep = chartWidth / (dataPoints.size - 1)
        
        val path = Path()
        val fillPath = Path()
        
        // Smooth line using Cubic Bezier
        for (i in 0 until dataPoints.size) {
            val x = paddingX + i * xStep
            val y = paddingY + dataPoints[i] * chartHeight
            
            if (i == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, chartHeight + paddingY)
                fillPath.lineTo(x, y)
            } else {
                val prevX = paddingX + (i - 1) * xStep
                val prevY = paddingY + dataPoints[i - 1] * chartHeight
                
                val cp1X = prevX + xStep / 2
                val cp1Y = prevY
                val cp2X = prevX + xStep / 2
                val cp2Y = y
                
                path.cubicTo(cp1X, cp1Y, cp2X, cp2Y, x, y)
                fillPath.cubicTo(cp1X, cp1Y, cp2X, cp2Y, x, y)
            }
            
            if (i == dataPoints.size - 1) {
                fillPath.lineTo(x, chartHeight + paddingY)
                fillPath.close()
            }
        }
        
        // Draw Area
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(ChartPink.copy(alpha = 0.4f), ChartPink.copy(alpha = 0.0f)),
                startY = paddingY,
                endY = chartHeight + paddingY
            )
        )
        
        // Draw Line
        drawPath(
            path = path,
            color = ChartPink,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        
        // Draw Points
        dataPoints.forEachIndexed { i, p ->
            val x = paddingX + i * xStep
            val y = paddingY + p * chartHeight
            drawCircle(color = ChartPink, radius = 5.dp.toPx(), center = Offset(x, y))
            drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(x, y))
        }
        
        // X-axis labels
        xLabels.forEachIndexed { i, label ->
            val x = paddingX + i * xStep
            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = labelStyle,
                topLeft = Offset(x - 10.dp.toPx(), height - 10.dp.toPx())
            )
        }
    }
}

@Composable
fun SymptomTrendsCard() {
    DashboardCard {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Symptom Trends", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = DarkText)
            Text(text = "Compared to last cycle", fontSize = 12.sp, color = GrayText)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                SymptomDonutChart()
            }
        }
    }
}

@Composable
fun SymptomDonutChart() {
    val sections = listOf(
        DonutSection("Mood", 0.30f, Lavender),
        DonutSection("Bloating", 0.31f, ChartPink),
        DonutSection("Acne", 0.17f, SoftMint),
        DonutSection("Fatigue", 0.22f, LightChartPink)
    )
    
    val textMeasurer = rememberTextMeasurer()
    
    Canvas(modifier = Modifier.size(200.dp)) {
        val radius = size.minDimension / 2.5f
        val innerRadius = radius * 0.6f
        val center = Offset(size.width / 2, size.height / 2)
        var startAngle = -90f
        
        sections.forEach { section ->
            val sweepAngle = section.percentage * 360f
            
            drawArc(
                color = section.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = (radius - innerRadius), cap = StrokeCap.Butt),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )
            
            // Draw floating labels
            val angleRad = (startAngle + sweepAngle / 2).toDouble() * (Math.PI / 180.0)
            val labelRadius = radius + 35.dp.toPx()
            val labelX = center.x + cos(angleRad).toFloat() * labelRadius
            val labelY = center.y + sin(angleRad).toFloat() * labelRadius
            
            // Draw shadow/circle for label (simplified shadow)
            drawCircle(
                color = Color.Black.copy(alpha = 0.05f),
                radius = 28.dp.toPx(),
                center = Offset(labelX, labelY + 2.dp.toPx())
            )
            drawCircle(
                color = Color.White,
                radius = 26.dp.toPx(),
                center = Offset(labelX, labelY)
            )
            
            val labelText = "${(section.percentage * 100).toInt()}%"
            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString(labelText),
                style = TextStyle(color = DarkText, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            )
            val subtextLayoutResult = textMeasurer.measure(
                text = AnnotatedString(section.label),
                style = TextStyle(color = GrayText, fontSize = 8.sp)
            )
            
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(labelX - textLayoutResult.size.width / 2, labelY - 12.dp.toPx())
            )
            drawText(
                textLayoutResult = subtextLayoutResult,
                topLeft = Offset(labelX - subtextLayoutResult.size.width / 2, labelY + 2.dp.toPx())
            )
            
            startAngle += sweepAngle
        }
    }
}

data class DonutSection(val label: String, val percentage: Float, val color: Color)

@Composable
fun LifestyleImpactCard() {
    var expanded by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("4 months") }
    val periods = listOf("1 month", "2 months", "3 months", "4 months", "6 months")

    DashboardCard {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Correlation Strength", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = DarkText)
                
                Box {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(LightGray)
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = selectedPeriod, fontSize = 12.sp, color = DarkText)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                    
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(CardWhite)
                    ) {
                        periods.forEach { period ->
                            DropdownMenuItem(
                                text = { Text(period, fontSize = 14.sp) },
                                onClick = {
                                    selectedPeriod = period
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            val rows = listOf(
                HeatmapRowData("Sleep", Lavender, listOf(1, 1, 1, 1, 0, 1, 1, 0, 1)),
                HeatmapRowData("Hydrate", ChartPink, listOf(1, 1, 0, 1, 1, 1, 0, 1, 1)),
                HeatmapRowData("Caffeine", SuccessGreen, listOf(0, 1, 1, 1, 1, 0, 1, 1, 1)),
                HeatmapRowData("Exercise", ChartPink, listOf(1, 0, 1, 1, 0, 1, 1, 1, 0))
            )
            
            rows.forEach { row ->
                HeatmapRow(row)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun HeatmapRow(data: HeatmapRowData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = data.label,
            modifier = Modifier.width(70.dp),
            fontSize = 13.sp,
            color = GrayText
        )
        
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            data.blocks.forEach { isActive ->
                Box(
                    modifier = Modifier
                        .size(width = 30.dp, height = 32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isActive == 1) {
                                Brush.verticalGradient(
                                    colors = listOf(data.color.copy(alpha = 0.6f), data.color)
                                )
                            } else {
                                SolidColor(LightGray.copy(alpha = 0.5f))
                            }
                        )
                )
            }
        }
    }
}

data class HeatmapRowData(val label: String, val color: Color, val blocks: List<Int>)

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(80.dp)
                .shadow(15.dp, RoundedCornerShape(40.dp), spotColor = Color.Black.copy(alpha = 0.1f)),
            color = Color.White,
            shape = RoundedCornerShape(40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(Icons.Default.Home, "Home", selectedTab == "Home") { onTabSelected("Home") }
                NavItem(Icons.Default.DateRange, "Track", selectedTab == "Track") { onTabSelected("Track") }
                NavItem(Icons.Default.Info, "Insights", selectedTab == "Insights") { onTabSelected("Insights") }
            }
        }
        
        Surface(
            modifier = Modifier
                .size(80.dp)
                .shadow(15.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.1f))
                .clickable { /* Handle Add Click */ },
            color = Color.White,
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = GrayText,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun NavItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) DarkText else GrayText,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) DarkText else GrayText,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
