<template>
    <div class="left">
      <input type="button" class="button" value="Compose" @click="showCompose()">
      <input type="button" class="button" value="Inbox" @click="showInbox()">
      <input type="button" class="button" value="Sent" @click="showSent()">
      <input type="button" class="button" value="Drafts" @click="showDraft()">
      <input type="button" class="button" value="Trash" @click="showTrash()">
      <input type="button" class="button" value="Contacts" @click="showContacts()">
      <input type="button" class="button" value="Profile" @click="showProfile()">
      <input type="button" class="button" value="Log out" @click="showLogin()">
    </div>   
</template>

<script>
import router from '@/router';
    export default{
        data(){
            return{

            }
        }
        ,methods:{
          hide (elements) {
              elements = elements.length ? elements : [elements];
              for (var index = 0; index < elements.length; index++) {
                elements[index].style.display = 'none';
              }
          }

          ,show (elements, specifiedDisplay) {
              elements = elements.length ? elements : [elements];
              for (var index = 0; index < elements.length; index++) {
                elements[index].style.display = specifiedDisplay || 'block';
              }
          }

          ,showLogin(){
              router.push({name:'Login'})
          }

          ,showInbox(){
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.show(document.getElementById("inbox"))
              this.emitter.emit("page",{msg: 'inbox'})
          }


          ,showCompose(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.show(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'compose'})
          }

          ,showSent(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.show(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'sent'})
          }

          ,showDraft(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.show(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'draft'})
          }

          ,showTrash(){
              this.hide(document.getElementById("inbox"))
              this.show(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'trash'})
          }

          ,showProfile(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.show(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'profile'})
          }

          ,showContacts(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.show(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.hide(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))
              this.emitter.emit("page",{msg: 'contacts'})
          }
        }
    }
</script>

<style>
.left {
  float: left;
  max-width: 200px;
  width: 100%;
  height: 780px;
  padding: 2rem;
  background: #fff;
  border-radius: 7px;
  box-shadow: 0 5px 10px rgba(0,0,0,0.3);
}
.left input.button{
  height: 60px;
  width: 100%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  color: #fff;
  background: #1f95c8;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
.left input.button:hover{
  background: #045787;
}
.left input.button:focus{
  background: #045787;
}
</style>