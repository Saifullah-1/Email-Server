<template>
<div class="signlog">
    <div class="registration form">
      <header>Signup</header>
      <form action="#">
        <input type="text" placeholder="First Name" v-model ="FirstName">
        <input type="text" placeholder="Last Name" v-model ="LastName">
        <input type="text" placeholder="Enter your email" v-model ="email">
        <input type="password" placeholder="Create a password" v-model ="password">
        <input type="button" class="button" value="Signup" @click="signUp">
      </form>
      <div class="signup">
        <span class="signup">Already have an account?
            <router-link to = "/" style="text-decoration: none;"><label id="loginScrn">Login</label></router-link>
        </span>
      </div>
    </div>
</div>
</template>

<script>
import router from '@/router'

    export default{
        name: 'Signup'
        ,data(){
            return{
                FirstName: '',
                LastName: '',
                email: '',
                password: '',
                url: 'http://localhost:8080/mail',
            }
        }
        ,methods:{
            handleInput(){
              if(this.FirstName === '' ||this.LastName === '' ||this.email === '' ||this.password === ''){
                alert("Please Fill All The Fields")
                return false
              }
              else if(!this.email.includes('@') ||this.email.charAt(this.email.length - 1) == '@'){
                alert("The email entered is not valid")
                return false
              }
              return true
            }
           ,async signUp(){
              if(this.handleInput()){
                var user = {
                  firstName: this.FirstName,
                  lastName: this.LastName,
                  email: this.email,
                  password: this.password,
                }
                await fetch(this.url+'/signup',{
                   method: 'POST'
                   , body: JSON.stringify(user)
                  }
                )
                .then(response=> {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text(); 
                })
                .then(data=>{
                    if(data === "Email already exists."){
                        alert(data)
                    }
                    else if(data === "Enter a valid email."){
                        alert(data)
                    }
                    else{
                        const info = JSON.parse(data)
                        router.push({name:'Home'})
                        setTimeout(()=>{this.emitter.emit("sign-up", { msg: user})},0)
                    }
                })
                .catch(error =>{
                  console.error('Error:', error);
                })
              }
          } 
        }
    }
</script>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
}
.signlog{
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

.signlog .form{
  padding: 2rem;
}
.signlog .form header{
  font-size: 2rem;
  font-weight: 500;
  text-align: center;
  margin-bottom: 1.5rem;
}
.signlog .form input{
  height: 60px;
  width: 100%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}
.signlog .form input:focus{
  box-shadow: 0 1px 0 rgba(0,0,0,0.2);
}
#birthday {
  height: 60px;
  width: 62%;
  margin-left: 1rem;
  color: #868686;
}
.signlog .form input.button{
  color: #fff;
  background: #1f95c8;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
.signlog .form input.button:hover{
  background: #045787;
}
.signup{
  font-size: 17px;
  text-align: center;
}
.signup label{
  color: #1f95c8;
  cursor: pointer;
}
.signup label:hover{
  text-decoration: underline;
}
</style>