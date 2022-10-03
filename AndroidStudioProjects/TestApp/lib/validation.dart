import 'package:flutter/material.dart';
import 'homePage.dart';

class Validation extends StatefulWidget{
  const Validation({super.key});

  @override
  State<StatefulWidget> createState() {
    return _ValidationState();
  }
}

class _ValidationState extends State<Validation> {

  final _userName = "buddy@gmail.com";
  final _passWord = "buddy123";
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    final GlobalKey<ScaffoldState> scaffoldKey = GlobalKey<ScaffoldState>();

    return Scaffold(
      resizeToAvoidBottomInset : false,
      key: scaffoldKey,
      appBar: AppBar(
        title: const Center(
            child: Text("Login Form",
              style: TextStyle(
                color: Colors.black,
              ),
            ),
        ),
        //backgroundColor: Colors.white,
      ),
      body: Container(
        padding: const EdgeInsets.only(left:40, right:40),
        child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 150,),
                const Text("Enter Details", style: TextStyle(fontSize: 30, color: Colors.black),),
                const SizedBox(height: 20,),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Enter User Name",
                  ),
                  validator: (value){
                    if(value!.isEmpty || value != _userName) {
                      return "Wrong username";
                    }else{
                      return null;
                    }
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Enter Password",
                  ),
                  obscureText: true,
                  autocorrect: false,
                  enableSuggestions: false,
                  validator: (value){
                    if(value!.isEmpty || value != _passWord){
                      return "Wrong password";
                    }else{
                      return null;
                    }
                  },
                ),
                const SizedBox(height: 15,),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: (){
                        if(_formKey.currentState!.validate()){
                          Navigator.of(context)
                              .push(MaterialPageRoute(
                            builder: (context) => const HomePage()
                          )
                          );
                        }
                      },
                      child: const Text("submit")
                    ),
                  ],
                )
              ],
            )
      ),
      ),
    );
  }
}