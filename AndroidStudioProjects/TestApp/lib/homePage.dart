import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hello/hello.dart';

class HomePage extends StatefulWidget{
  const HomePage({super.key});

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _HomePageState();
  }

}

class _HomePageState extends State<HomePage>{
  Hello hello = Hello();
  String _deviceName = "Unknown";

  @override
  void initState() {
    super.initState();
    initDeviceSate();
  }

  Future<void> initDeviceSate() async {
    String? deviceName;
    try{
      deviceName = await hello.getDeviceName();
    }on PlatformException {
      deviceName = "Failed to get device name";
    }
    if(!mounted) return;
    setState(() {
      _deviceName = deviceName.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: const Text("Home"),
      ),
      body: Center(
        child: Text('Plugin shows $_deviceName'),
      ),
    );
  }
}