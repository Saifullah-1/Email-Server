<template>
    <div class="log">
        <div class="login form">
          <header>Login</header>
          <form action="#">
            <input type="email" placeholder="Enter your email" v-model="email">
            <input type="password" placeholder="Enter your password" v-model ="password">
            <input type="button" class="button" value="Login" @click="LogIn()">
          </form>
          <div class="signup">
            <span class="signup">Don't have an account?
             <router-link to = "/Signup" style="text-decoration: none;"><label id="signupScrn">Signup</label></router-link>
            </span>
          </div>
        </div>
    </div>
    </template>
    
    <script>
      import router from '@/router'
        export default{
            data(){
                return{
                  email: '',
                  password: '',
                  url: '',
                }
            }
            ,methods:{
              handleInput(){
                if(this.email === '' ||this.password === ''){
                  alert("Please Fill All The Fields")
                  return false
                }
                else if(!this.email.includes('@') ||this.email.charAt(this.email.length - 1) == '@'){
                 alert("The email entered is not valid")
                 return false
                }
                return true
              }
              ,async LogIn(){
                if(this.handleInput()){
                  var user = {
                    email: this.email,
                    password: this.password,
                  }
                  /*await fetch(url+'/login',
                  method: 'POST'
                  body: JSON.stringify(user)
                )
                .then(response=> {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text(); 
                })
                .then(data=>{
                    
                })
                .catch(error =>{
                  console.error('Error:', error);
                })*/
                  router.push({name:'Home'})
                }
              }               
            }
        }
    </script>
    
    <style>
    .log{
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      max-width: 430px;
      width: 100%;
      background: #fff;
      border-radius: 7px;
      box-shadow: 0 5px 10px rgba(0,0,0,0.3);
    }
    .log .form{
      padding: 2rem;
    }
    .log .form header{
      font-size: 2rem;
      font-weight: 500;
      text-align: center;
      margin-bottom: 1.5rem;
    }
    .log .form input{
      height: 60px;
      width: 100%;
      padding: 0 15px;
      font-size: 17px;
      margin-bottom: 1.3rem;
      border: 1px solid #ddd;
      border-radius: 6px;
      outline: none;
    }
    .log .form input:focus{
      box-shadow: 0 1px 0 rgba(0,0,0,0.2);
    }
    #birthday {
      height: 60px;
      width: 62%;
      margin-left: 1rem;
      color: #868686;
    }
    .log .form input.button{
      color: #fff;
      background: #009579;
      font-size: 1.2rem;
      font-weight: 500;
      letter-spacing: 1px;
      cursor: pointer;
      transition: 0.4s;
    }
    .log .form input.button:hover{
      background: #006653;
    }
    .signup{
      font-size: 17px;
      text-align: center;
    }
    .signup label{
      color: #009579;
      cursor: pointer;
    }
    .signup label:hover{
      text-decoration: underline;
    }
    </style>