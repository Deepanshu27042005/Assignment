# Assignment
Project Overview
This project is a high-fidelity recreation of a complex Figma "Insights" dashboard, developed as part of a technical assessment. The objective was to build a pixel-perfect, interactive mobile interface using Jetpack Compose, emphasizing custom data visualization without the use of third-party libraries.

#Technical Accomplishments

1. Custom Data Visualization (Canvas API) I implemented all charts and graphs from scratch using the Compose Canvas API. This demonstrates a deep understanding of coordinate systems, path geometry, and rendering performance:
• Stability Summary Chart: A dual-layered shaded line chart featuring custom path rendering and a coordinate-mapped tooltip with a triangular pointer.
• Cycle Trends: A multi-segment bar chart with vertical rounded-corner logic and specific segment color-coding.
• Body & Metabolic Trends: A smooth line chart utilizing Cubic Bezier curves for fluid data representation and dynamic point mapping.
• Symptom Trends: A donut chart featuring proportional arc calculations and trigonometric logic to position floating labels and shadows around the circumference.
• Lifestyle Impact Heatmap: A grid-based visualization using vertical linear gradients and rounded-box drawing logic.

2. State Management and Interactivity The dashboard is not a static mockup; it features functional interactivity driven by Compose State:
• Dynamic Data Switching: The Body & Metabolic Trends chart re-renders entirely different datasets and X-axis labels when toggled between "Monthly" and "Weekly" views.
• Navigation Logic: A fully functional floating bottom navigation bar that manages active tab states.
• Contextual Menus: An interactive dropdown menu in the Correlation Strength section that updates UI state upon selection.
• Period Navigation: Implemented state-driven updates for the Cycle Trends date ranges.

3.Advanced UI/UX Implementation
• Modern Mesh Gradients: Recreated the complex background tint using radial gradients and the drawBehind modifier to simulate high-end design aesthetics.
• Material 3 Integration: Leveraged the Material 3 design system for typography, cards, and surface elevation while maintaining custom design constraints.
• Responsive Layout: Built with a flexible column structure and vertical scrolling to ensure the dashboard scales correctly across different Android screen sizes and densities.
• Custom Shadows: Implemented soft, diffused shadows with specific spot colors to achieve the ultra-modern "floating" look required by the design.

4. Performance and Architecture
• Zero Library Overhead: By avoiding external chart libraries, the application maintains a minimal APK size and avoids third-party dependency conflicts.
• Component Reusability: Engineered modular composables (e.g., DashboardCard, SectionTitle, NavItem) to ensure code maintainability and clear separation of concerns.
• Clean Code Standards: Followed idiomatic Kotlin and Jetpack Compose best practices, including organized color constants and structured state hoisting.

#Project Structure
• Entry Point: MainActivity.kt handles the activity lifecycle and theme application.
• State Layer: InsightsScreen manages the overarching screen state and business logic for data toggles.
• Presentation Layer: Modular composables for each section (Stability, Cycle, Weight, Symptoms, Lifestyle) handle the specialized rendering logic.
• Graphics Layer: Dedicated Canvas drawing functions manage the mathematical transformations required for the charts.
