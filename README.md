Insights Dashboard – Android (Jetpack Compose)
Project Overview

This project is a high-fidelity recreation of a complex Figma "Insights" dashboard, developed as part of a technical assessment. The objective was to build a pixel-perfect, interactive mobile interface using Jetpack Compose, with a strong emphasis on custom data visualization without relying on third-party libraries.

Technical Accomplishments
1. Custom Data Visualization (Canvas API)

All charts and graphs have been implemented from scratch using the Compose Canvas API, demonstrating a strong understanding of coordinate systems, path geometry, and rendering performance.

Stability Summary Chart
Dual-layer shaded line chart with custom path rendering and a coordinate-mapped tooltip featuring a triangular pointer.
Cycle Trends
Multi-segment bar chart with vertical rounded-corner logic and structured segment color-coding.
Body & Metabolic Trends
Smooth line chart built using cubic Bézier curves for fluid data representation and precise point mapping.
Symptom Trends
Donut chart using proportional arc calculations, with trigonometric positioning of floating labels and shadows.
Lifestyle Impact Heatmap
Grid-based visualization using rounded boxes and gradient-based intensity representation.
2. State Management and Interactivity

The dashboard is fully interactive and not a static UI mockup. All interactions are driven by Jetpack Compose state.

Dynamic Data Switching
The Body & Metabolic Trends chart updates datasets and axis labels when toggling between "Monthly" and "Weekly" views.
Navigation Logic
A floating bottom navigation bar manages active tab states.
Contextual Menus
Interactive dropdown menu in the Correlation Strength section updates UI state dynamically.
Period Navigation
Cycle Trends section supports state-driven updates for different date ranges.
3. Advanced UI/UX Implementation
Modern Mesh Gradients
Background tint recreated using radial gradients and the drawBehind modifier to simulate a refined visual effect.
Material 3 Integration
Uses Material 3 for typography, cards, and surface elevation while maintaining custom design constraints.
Responsive Layout
Flexible column-based layout with vertical scrolling to ensure compatibility across different screen sizes and densities.
Custom Shadows
Soft, diffused shadows implemented to achieve a modern floating UI appearance.
4. Performance and Architecture
Zero Library Overhead
No external charting libraries are used, ensuring a lightweight application and avoiding dependency conflicts.
Component Reusability
Modular composables such as DashboardCard, SectionTitle, and NavItem improve maintainability and readability.
Clean Code Practices
Follows idiomatic Kotlin and Jetpack Compose standards with clear separation of concerns and structured state handling.
Project Structure
MainActivity.kt
│
├── InsightsScreen (State Layer)
│   ├── HeaderSection
│   ├── SectionTitle
│   ├── StabilitySummaryCard
│   ├── CycleTrendsCard
│   ├── WeightTrendCard
│   ├── SymptomTrendsCard
│   ├── LifestyleImpactCard
│   └── BottomNavBar
│
├── Reusable Components
│   ├── DashboardCard
│   ├── NavItem
│   └── UI Utilities
│
└── Graphics Layer
    └── Canvas-based drawing logic for all charts
