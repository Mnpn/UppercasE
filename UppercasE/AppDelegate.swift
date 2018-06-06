//
//  AppDelegate.swift
//  UppercasE
//
//  Created by Mnpn on 06/05/2018.
//  Copyright © 2018 Mnpn. All rights reserved.
//

import Cocoa
import AppKit

@NSApplicationMain
class AppDelegate: NSObject, NSApplicationDelegate {
    // Create outlets.
    @IBOutlet weak var input: NSTextField!
    @IBOutlet weak var output: NSTextField!
    @IBOutlet weak var autoUpdate: NSButton!
    
    // Get input from NSTextField as a string.
    lazy var name: String = input.stringValue

    func applicationDidFinishLaunching(_ aNotification: Notification) {
        // Insert code here to initialize your application
    }

    func applicationWillTerminate(_ aNotification: Notification) {
        // Insert code here to tear down your application
    }
    
    // UppercasE button's function
    @IBAction func executeThePlan(_ sender: NSButton) {
        output.stringValue = convert(inputtext: input.stringValue)
    }
    
    // Copy output to clipboard
    @IBAction func copy(_ sender: Any) {
        NSPasteboard.general.clearContents()
        NSPasteboard.general.setString(output.stringValue, forType: .string)
    }
    
    // The function that UppercasEs
    func convert(inputtext: String) -> String {
        var outputtext: String = ""
        for char in inputtext.lowercased() {
            // If a random value between 0 and 1 is 0, make the character uppercase.
            if arc4random_uniform(2) == 0 {
                outputtext += String(char).uppercased()
            } else {
                outputtext += String(char)
            }
        }
        return outputtext
    }
    
    @IBAction func onUpdate(_ sender: NSTextField) {
        if autoUpdate.state == NSControl.StateValue.on {
            output.stringValue = convert(inputtext: input.stringValue)
        }
        print(output.stringValue)
    }
    
    // Open my contact website in the default browser
    @IBAction func openWeb(_ sender: Any) {
        NSWorkspace.shared.open(URL(string: "https://mnpn.hisses-at.me/contact")!)
    }
    
    // Open a file and make it the input
    @IBAction func openFile(_ sender: Any) {
        let openPanel = NSOpenPanel();
        openPanel.isExtensionHidden = false
        openPanel.begin { (result) -> Void in
            let path = openPanel.url
            do {
                if path != nil { // Make sure the path isn't nil. If it is, the program will fail.
                    self.input.stringValue = try String(contentsOf: path!, encoding: .utf8)
                }
            } catch {
                // Failed to read file. Bad permission, bad filename?
                let alert = NSAlert()
                alert.messageText = "Couldn't read file due to an error"
                // Use Xcode's program title.
                alert.informativeText = Bundle.main.infoDictionary![kCFBundleNameKey as String] as! String + " could not read the file. This probably happened because of a bad permission or filename. If you believe you're seeing this message in error, please contact me via the help dialogue."
                alert.alertStyle = .warning
                alert.addButton(withTitle: "OK")
                alert.runModal()
            }
        }
    }
    
    // Save the output to a file
    @IBAction func saveFile(_ sender: Any) {
        let savePanel = NSSavePanel()
        savePanel.isExtensionHidden = false
        savePanel.allowedFileTypes = ["txt"]
        savePanel.begin { (result) -> Void in
            let filename = savePanel.url
            do {
                try self.output.stringValue.write(to: filename!, atomically: true, encoding: String.Encoding.utf8)
            } catch {
                // Failed to write to file. Bad permission, bad filename?
                let alert = NSAlert()
                alert.messageText = "Couldn't save file due to an error"
                // Use Xcode's program title.
                alert.informativeText = Bundle.main.infoDictionary![kCFBundleNameKey as String] as! String + " could not save the file to the desired location. This probably happened because of a bad permission or filename. If you believe you're seeing this message in error, please contact me via the help dialogue."
                alert.alertStyle = .warning
                alert.addButton(withTitle: "OK")
                alert.runModal()
            }
        }
    }
}
