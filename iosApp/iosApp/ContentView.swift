import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    init() {
        print("Token -> \(Singleton.shared.getToken())")
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea() // Compose has own keyboard handler
    }
}
