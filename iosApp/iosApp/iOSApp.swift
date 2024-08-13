import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        DiKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            HomeScreen()
            // ContentView() // Uncomment to load compose view instead of native SwiftUI
        }
    }
}
