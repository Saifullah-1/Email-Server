<template>
<div class="signlog">
    <div class="login form">
      <header>Login</header>
      <form action="#">
        <input type="email" placeholder="Enter your email">
        <input type="password" placeholder="Enter your password">
        <input type="button" class="button" value="Login">
      </form>
      <div class="signup">
        <span class="signup">Don't have an account?
         <RouterLink to = "'/Signup'"><label id="signupScrn">Signup</label></RouterLink>
        </span>
      </div>
    </div>
</div>
</template>

<script>
    export default{
        data(){
            return{

            }
        }
        ,methods:{

        }
    }
</script>

<style>
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
.signlog .registration{
  display: none;
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
  background: #009579;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
.signlog .form input.button:hover{
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