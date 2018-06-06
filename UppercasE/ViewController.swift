//
//  ViewController.swift
//  UppercasE
//
//  Created by Mnpn on 06/05/2018.
//  Copyright © 2018 Mnpn. All rights reserved.
//

import Cocoa

class ViewController: NSViewController, NSTextViewDelegate {
    @IBOutlet weak var textField: NSTextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        textField.delegate = self as? NSTextFieldDelegate
    }

    override var representedObject: Any? {
        didSet {
        // Update the view, if already loaded.
        }
    }
    func controlTextDidChange(notification: NSNotification) {
        print("update")
    }
    func textDidChange(_ notification: Notification) {
        print("hiss")
    }


}
