<template>
  <div class="main">
    <sidebar></sidebar>
    <div class="right">

      <div id="inbox">
        <header>Inbox</header>
        <BarInbox></BarInbox>
        <div class="content">
          <Mail v-for="inbox in Inboxs" :mail ="inbox"></Mail>
        </div>
      </div>

      <div id="trash">
        <header>Trash</header>
        <BarTrash></BarTrash>
        <div class="content">
          <Mail v-for="trash in trashs" :mail ="trash"></Mail>
        </div>
      </div>
 
      <div id="sent">
        <header>Sent</header>
        <BarSent></BarSent>
        <div class="content">
          <Mail v-for="sent in sents" :mail = "sent"></Mail> 
        </div>
      </div>

      <div id="mail">
        <br>
        <p class="from">from :&nbsp;{{ mfrom }}</p>
        <p class="subject">subject : &nbsp;{{ msubject}}</p>
        <br>
        <label class="body">Body:</label>
        <br>
        <p class="body">{{ mbody }}</p>
      </div>

      <div id="compose" style="position: relative;height: 730px;width: 100%;">
        <header>Compose</header>
        <form style="position: relative;">
          <label for="to" style="font-size: 17px; margin-left: 10px;margin-right: 42px;position: absolute;top: 10px;">To:</label>
          <input type="text" id="to" placeholder="To" v-model ="mailTo">
        </form>
        <form style="position: relative;">
          <label for="subject" style="font-size: 17px; margin-left: 10px;position: absolute;top: 10px;">Subject:</label>
          <input type="text" id="subject" placeholder="Subject" v-model="mailSubject">
        </form>
        <form style="height: 430px;position: relative;">
          <label for="body" style="font-size: 17px; margin-left: 10px;margin-right: 20px;position: absolute;top: 10px;">Body:</label>
          <textarea id="body" placeholder="Body" v-model ="mailBody"></textarea>
        </form>
        <input type="file" id="myFile" name="filename">
        <input type="button" class="button" style="margin-left: 80px;right: 250px;" value="Close" @click="Draft()">
        <input type="button" class="button" style="margin-left: 30px;" value="Send" @click="sendMail">
      </div>

      <div id="draft">
        <header>Drafts</header>
        <BarDraft></BarDraft>
        <div class="content">
          <Mail v-for="draft in drafts" :mail ="draft"></Mail>
        </div>
      </div>

      <div id="contacts">
        <header>Contacts</header>
        <div class="big">
          <div class="contacts">
            <input type="button" class="button" value="&plus;&emsp;Create Contact" @click ="showCreateContact()">
            <table>
              <contact v-for="contact in contacts" :contactInfo ="contact"></contact>
            </table>
          </div>

          <div class="single-contact">
            <img src="..\assets\images\profile-com.svg">
            <table style="text-align: left;margin-left: 28%;margin-top: 20px;">
              <tr>
                <th>First Name:</th>
                <input type="text" :placeholder="contactFirstName" v-model="editContactFirstName">
              </tr>
              <tr>
                <th>Last Name:</th>
                <input type="text" :placeholder="contactLastName" v-model="editContactLastName">
              </tr>
              <tr>
                <th>Mail:</th>
                <input type="text" id ='email-input' :placeholder="contactEmail" v-model="editContactEmail">
              </tr>
              <input type="button" class="button" value="Save" @click="editContact()">
            </table>
          </div>

          <div class="create-contact" id="create" style="overflow-y: scroll;">
            <img src="../assets/images/profile-com.svg">
            <form id = 'input'>
              <input type="text" placeholder="First Name" v-model="createContactFirstName">
              <input type="text" placeholder="Last Name" v-model="createContactLastName">
              <input type="text" id ='email-input' placeholder="Email" v-model="createContactEmail">
            </form>
            <form>
              <input type="button" class="button" value="&plus;&emsp;Add email" style="width: 180px;" @click="createEmail()">
              <input type="button" class="button" value="Save" @click="createContact()">
            </form>
          </div>

        </div>
      </div>
      <div id="user-folder">
        <header>Profile</header>
        <div class="profile">
          <img src="..\assets\images\profile-com.svg">
          <table style="text-align: left;margin-left: 40%;margin-top: 20px;">
            <tr>
              <th>First Name:</th>
              <td>{{currentUserFirstName}}</td>
            </tr>
            <tr>
              <th>Last Name:</th>
              <td>{{ currentUserLastName }}</td>
            </tr>
            <tr>
              <th>Mail:</th>
              <td>{{ currentUserEmail }}</td>
            </tr>
          </table>
        </div>
      </div>    
    </div>
  </div>

</template>

<script>
  import contact from '@/components/contact.vue'
  import Mail from '@/components/Mail.vue';
  import sidebar from '@/components/sidebar.vue';
  import axios from 'axios';
  import BarInbox from '@/components/BarInbox.vue';
  import BarDraft from '@/components/BarDraft.vue';
  import BarSent from '@/components/BarSent.vue';
  import BarTrash from '@/components/BarTrash.vue';
 
    export default{
        name: 'Home'
        ,components:{
            sidebar,
            Mail,
            contact,
            BarInbox,
            BarDraft,
            BarSent,
            BarTrash
        }
        ,data(){
           return{
            //global
              currentUserFirstName:'',
              currentUserLastName:'',
              currentUserEmail:'',
              server: 'http://localhost:8080/mail',
              currentPage: 'inbox',
              SortType:'',
              searchText:'',
              mfrom: '',
              msubject:'',
              mbody:'',
            //compose
              mailTo: '',
              mailSubject: '',
              mailBody: '',
              ID: 1,
            //InBox
              Inboxs: [],
            //sent
              sents: [],
            //Drafts
              drafts: [],
            //Trash
              trashs: [],
            //contacts
              contacts:[],
              contactFirstName:'',
              contactLastName:'',
              contactDateOfBirth: '',
              contactEmail:'',
              createContactFirstName:'',
              createContactLastName:'',
              createContactEmail:'',
              editContactFirstName:'',
              editContactLastName:'',
              editContactEmail:'',
              contactID: 0,
           }
        }
        ,methods:{
          async sendMail() {
            console.log("1111"+ this.currentUserEmail);
            let inputString = this.mailTo;
            let delimiter = ",";
            let resultArray = inputString.split(delimiter);
            var mail = {
              from: this.currentUserEmail,
              to: resultArray,
              subject: this.mailSubject,
              body: this.mailBody,
            };

            const response = await axios.post(this.server + '/mailto', mail);
            console.log(mail);
          }
          ,async Draft(){
            let inputString = this.mailTo;
            let delimiter = ",";
            let resultArray = inputString.split(delimiter);
            var mail={
               from: this.currentUserEmail,
                to: resultArray,
                subject: this.mailSubject,
                body: this.mailBody,
              }
              const response = await axios.post(this.server+'/draft',mail)
              this.showDraft()
              this.handleDrafts()
          }
          ,async handleInbox(){
             const response = await axios.get(this.server+'/inboxF')
             let mailArray = response.data
             this.Inboxs =[]
             for(var i = 0 ;i < mailArray.length ;i++){
                var mail ={
                  ID: mailArray[i].ID,
                  to: mailArray[i].to,
                  from: mailArray[i].from,
                  subject: mailArray[i].subject,
                  body: mailArray[i].body
                }
                this.Inboxs.push(mail)
             }
             console.log(this.inbox)
          }
          
          ,async handleSent(){
            const response = await axios.get(this.server+'/sentF')
            let mailArray = response.data
            this.sents =[]
            for(var i = 0 ;i < mailArray.length ;i++){
              var mail ={
                ID: mailArray[i].ID,
                to: mailArray[i].to,
                from: mailArray[i].from,
                subject: mailArray[i].subject,
                body: mailArray[i].body
              }
              this.sents.push(mail)
            }
          }
          ,async handleDrafts(){
            const response = await axios.get(this.server+'/draftF')
            let mailArray = response.data
            this.drafts =[]
            for(var i = 0 ;i < mailArray.length ;i++){
              var mail ={
                ID: mailArray[i].ID,
                to: mailArray[i].to,
                from: mailArray[i].from,
                subject: mailArray[i].subject,
                body: mailArray[i].body
              }
              this.drafts.push(mail)
            }
          }
          ,async handleTrash(){
            const response = await axios.get(this.server+'/trashF')
            let mailArray = response.data
            this.trashs =[]
            for(var i = 0 ;i < mailArray.length ;i++){
              var mail ={
                ID: mailArray[i].ID,
                to: mailArray[i].to,
                from: mailArray[i].from,
                subject: mailArray[i].subject,
                body: mailArray[i].body
              }
              this.trashs.push(mail)
            }
          }
          ,async handleContacts(){
            const response = await axios.get(this.server+'/contactsF')
            let contactArray = response.data
            this.contacts =[]
            for(var i = 0 ;i < contactArray.length ;i++){
              var contact ={
                ID: contactArray[i].ID,
                firstName: contactArray[i].firstName,
                lastName: contactArray[i].lastName,
                email: contactArray[i].email,
              }
              this.contacts.push(contact)
            }
          }
          
          ,async handleSort(folder){
            const response = await axios.get(this.server+'/sort?folder='+folder+'&method='+this.SortType)
            let mailArray = response.data
            console.log(mailArray)
            this.load(folder,mailArray)
          }
          ,async handleSearch(folder){
            console.log(this.server+'/search?folder='+folder,this.searchText)
            const response = await axios.post(this.server+'/search?folder='+folder,{
              key: this.searchText
            })
            let mailArray = response.data
            this.load(folder,mailArray)
          }
          ,async handleFilter(to,subject){
            console.log(to)
            console.log(subject)
            if(to == ''){
              const response = await axios.get(this.server+'/filter?section='+this.currentPage+'&key='+subject)
              let mailArray = response.data
              this.load(this.currentPage,mailArray)
            }
            else if(subject = ''){
              const response = await axios.get(this.server+'/filter?section='+this.currentPage+'&value='+to)
              let mailArray = response.data
              this.load(this.currentPage,mailArray)
            }
            else{
              const response = await axios.get(this.server+'/filter?section='+this.currentPage+'&key='+subject+'&value='+to)
              let mailArray = response.data
              this.load(this.currentPage,mailArray)
            }
          }
          ,load(folder,mailArray){
              if(folder === 'inbox'){
                this.Inboxs =[]
                for(var i = 0 ;i < mailArray.length ;i++){
                    var mail ={
                      ID: mailArray[i].ID,
                      to: mailArray[i].to,
                      from: mailArray[i].from,
                      subject: mailArray[i].subject,
                      body: mailArray[i].body,
                      favourite: mailArray[i].favourite
                    }
                    this.Inboxs.push(mail)
                }
              }
              else if(folder ==='sent'){
                this.sents =[]
                for(var i = 0 ;i < mailArray.length ;i++){
                  var mail ={
                    ID: mailArray[i].ID,
                    to: mailArray[i].to,
                    from: mailArray[i].from,
                    subject: mailArray[i].subject,
                    body: mailArray[i].body,
                    favourite: mailArray[i].favourite
                  }
                  this.sents.push(mail)
                }
              }
              else if(folder === 'draft'){
                this.drafts =[]
                for(var i = 0 ;i < mailArray.length ;i++){
                  var mail ={
                    ID: mailArray[i].ID,
                    to: mailArray[i].to,
                    from: mailArray[i].from,
                    subject: mailArray[i].subject,
                    body: mailArray[i].body,
                    favourite: mailArray[i].favourite
                  }
                  this.drafts.push(mail)
                }
              }
              else if(folder === 'trash'){
                this.trashs =[]
                for(var i = 0 ;i < mailArray.length ;i++){
                  var mail ={
                    ID: mailArray[i].ID,
                    to: mailArray[i].to,
                    from: mailArray[i].from,
                    subject: mailArray[i].subject,
                    body: mailArray[i].body,
                    favourite: mailArray[i].favourite
                  }
                  this.trashs.push(mail)
                }
              }
          }
          ,async handleDelete(selected){
             //let mailArray
             for(var i = 0; i < selected.length;i++){

                  const response =await axios.delete(this.server+'/delete?folder='+this.currentPage+'&id='+selected[i])

             }
             if(this.currentPage =='inbox'){
                this.handleInbox()
             }
             else  if(this.currentPage =='sent'){
                this.handleSent()
             }
             else  if(this.currentPage =='draft'){
                this.handleDrafts()
             }
             else  if(this.currentPage =='trash'){
                this.handleTrash()
             }
             
             
          } 
          ,async createContact(){
            this.showCreateContact()
            console.log(this.createContactEmail)
            let newContact ={
               ID: this.contactID++,
               firstName:this.createContactFirstName,
               lastName: this.createContactLastName,
               email: [this.createContactEmail]
            }
            console.log(this.createContactEmail)
            const response = await axios.post(this.server + '/createContact',newContact)
            let contactArray = response.data
            this.contacts =[]
            for(var i = 0;i < contactArray.length ; i++){
               var existingContact = {
                  ID: contactArray[i].ID,
                  firstName:contactArray[i].firstName,
                  lastName:contactArray[i].lastName,
                  email:contactArray[i].email
               }
               this.contacts.push(existingContact)
            }
            this.createContactFirstName =''
            this.createContactLastName =''
            this.createContactEmail =''
            this.hideCreateContact()
          }       
          ,async editContact(){
              if(this.editContactFirstName != this.contactFirstName){
                const response = await axios.post(this.server + '/editUser?field='+this.editContactFirstName+'&replace='+this.contactFirstName)
              }
              else if(this.editContactLastName != this.contactLastName){
                const response = await axios.post(this.server + '/editUser?field='+this.editContactLasttName+'&replace='+this.contactLastName)
              }
              else if(this.editContactEmail != this.editContactEmail){
                const response = await axios.post(this.server + '/editUser?field='+this.editContactLasttName+'&replace='+this.contactLastName)
              }
          }
          
          ,async deleteContact(id){
            const response = await axios.delete(this.server + '/deleteContact?id='+id)
            let contactArray = response.data
            this.contacts =[]
            for(var i = 0;i < contactArray.length ; i++){
               var existingContact = {
                  ID: contactArray[i].ID,
                  firstName:contactArray[i].firstName,
                  lastName:contactArray[i].lastName,
                  email:contactArray[i].email
               }
               this.contacts.push(existingContact)
            }
          }
          ,async star(id){
            const response = await axios.post(this.server+'/star?folder='+this.currentPage+'&id='+id)
          }
          ,hide (elements) {
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
          

          ,showCreateContact(){
              this.hide(document.getElementsByClassName("single-contact"))
              let input = document.createElement('input');
              input.placeholder = 'Email';
              document.getElementById("email-input").replaceChildren()
              document.getElementById("email-input").appendChild(input)
              this.show(document.getElementsByClassName("create-contact"))
          }

          ,hideCreateContact(){
              this.hide(document.getElementsByClassName("create-contact"))
          }

          ,showSingleContact(){
              this.show(document.getElementsByClassName("single-contact"))
          }

          ,showFilterInbox() {
              var x = document.getElementById("search-inbox")
              x.style.display = "block"
          }

          ,hideFilterInbox() {
              var x = document.getElementById("search-inbox")
              x.style.display = 'none'
          }


          ,showFilterTrash() {
              var x = document.getElementById("search-trash")
              x.style.display = "block"
          }

          ,hideFilterTrash() {
              var x = document.getElementById("search-trash")
              x.style.display = 'none'
          }

          ,showFilterSent() {
              var x = document.getElementById("search-sent")
              x.style.display = "block"
          }

          ,hideFilterSent() {
              var x = document.getElementById("search-sent")
              x.style.display = 'none'
          }

          ,showFilterDraft() {
              var x = document.getElementById("search-draft")
              x.style.display = "block"
          }

          ,hideFilterDraft() {
              var x = document.getElementById("search-draft")
              x.style.display = 'none'
          }
          ,showContent(){
              this.hide(document.getElementById("inbox"))
              this.hide(document.getElementById("trash"))
              this.hide(document.getElementById("sent"))
              this.hide(document.getElementById("user-folder"))
              this.hide(document.getElementById("contacts"))
              this.hide(document.getElementById("compose"))
              this.show(document.getElementById("mail"))
              this.hide(document.getElementById("draft"))

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
          }
          ,hideCreateContact(){
              this.hide(document.getElementsByClassName("create-contact"))
          }
          ,createEmail(){
              let input = document.createElement('input');
              input.placeholder = 'Email';
              document.getElementById("email-input").appendChild(input);
          }
          
        },
        mounted(){
          this.emitter.on("sign-up", (data) => {
            let user = data.msg;
            this.currentUserFirstName = user.firstName;
            this.currentUserLastName = user.lastName;
            this.currentUserEmail = user.email;
            console.log("1111111"+this.currentUserEmail);
          });

          this.emitter.on("log-in", (data) => {
            this.Inboxs = data.msg1;
            this.currentUserEmail = data.msg2
            console.log("11111"+this.currentUserEmail);
            console.log(data.msg2)
          });

          this.emitter.on("page",(data)=>{
            this.currentPage = data.msg
            if(this.currentPage === 'inbox'){
               this.handleInbox()
            }
            else if(this.currentPage === 'sent'){
               this.handleSent()
            }
            else if(this.currentPage === 'draft'){
               this.handleDrafts()
            }
            else if(this.currentPage === 'trash'){
               this.handleTrash()
            }
            else if(this.currentPage === 'contacts'){
              this.handleContacts()
            }
            else if(this.currentPage === 'logout'){
              this.handleLogOut()
            }
          })
          this.emitter.on('single-contact',(data)=>{
              console.log(data.msg1)
              console.log(data.msg2)
              console.log(data.msg3)
              this.contactFirstName= data.msg1
              this.contactLastName = data.msg2
              this.contactEmail = data.msg3
              this.showSingleContact()
          })
          this.emitter.on('mail-content',(data)=>{
              this.showContent()
              this.mfrom = data.msg1
              this.msubject = data.msg2
              this.mbody = data.msg3
          })
          this.emitter.on("sort",(data)=>{
            this.SortType = data.msg
            this.handleSort(this.currentPage)
          })
          this.emitter.on("search",(data)=>{
            this.searchText = data.msg
            this.handleSearch(this.currentPage)
          })
          this.emitter.on("filter",(data)=>{
            this.handleFilter(data.msg1,data.msg2)
          })
          this.emitter.on("delete",(data)=>{
            this.handleDelete(data.msg)
          })
          this.emitter.on("starred",(data)=>{
            this.star(data.msg)
          })
          this.emitter.on("delete-contact",(data)=>{
            this.deleteContact(data.msg)
          })
        }
    }
</script>

<style>
.main {
  position: absolute;
  top: 50%;
  left: 50%;
  max-width: 1500px;
  width: 100%;
  transform: translate(-50%,-50%);
}
.right {
  float: right;
  max-width: 1280px;
  width: 100%;
  height: 780px;
  padding: 1rem;
  background: #fff;
  border-radius: 7px;
  box-shadow: 0 5px 10px rgba(0,0,0,0.3);
}
.content{
  margin-top: 20px;
  width: 100%;
  height: 520px;
  position: relative;
}
.content div{
  height: 30px;
}
.content div p{
  float: right;
  position: relative;
  width: 750px;
  height: 100%;
}
.content div:hover{
  background-color: #dddddd;
}
.star {
  margin-left: 10px;
  visibility:hidden;
  vertical-align: top;
  font-size:18px;
  cursor:pointer;
}
.star:before {
 content: "★";
 position: absolute;
 visibility:visible;
 color: #ffc700;
}
.star:checked:before {
 content: "★";
 position: absolute;
 color: #a6e6ee;
}
.from {
  font-weight: 500;
  font-size: large;
}
.subject {
  font-weight: 500;
  font-size: large;
}
.date {
  font-weight: 500;
  font-size: large;
  margin-bottom: 1rem;
}
.body {
  font-weight: 500;
  font-size: large;
  margin-bottom: 5px;
}
#myFile {
  position: absolute;
  left: 100px;
  height: 50px;
  width: 620px;
  padding: 0 0px;
  font-size: 17px;
  color: black;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}
#compose input[type="text"],#compose input[type="button"] {
  position: absolute;
  height: 50px;
  width: 91%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  right: 10px;
}
#compose input.button {
  position: absolute;
  width: 220px;
  height: 50px;
  color: #fff;
  background: #1f95c8;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
#compose input.button:hover {
  background: #045787;
}
#myFile::-webkit-file-upload-button {
  position: relative;
  width: 150px;
  height: 50px;
  margin-bottom: 1rem;
  color: #fff;
  background: #1f95c8;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}
#myFile::-webkit-file-upload-button:hover {
  background: #045787;
}
#compose form {
  height: 50px;
  width: 100%;
  margin-bottom: 20px;
}
#body {
  height: 430px;
  width: 91%;
  position: absolute;
  right: 10px;
  padding: 5px 15px;
  font-size: 17px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  resize: none;
}

/*User Folder part*/
.profile {
  text-align: center;
  width: 100%;
  height: 600px;
}
.profile table tr {
  height: 40px;
}

/*Contacts part*/
.big {
  background-color: #d6f1f5;
  position: relative;
  width: 100%;
  height: 690px;
  padding: 10px;
  border-radius: 7px;
  box-shadow: 0 5px 10px rgba(0,0,0,0.3);
}
.contacts {
  background-color: #fff;
  position: absolute;
  width: 48%;
  height: 670px;
  padding: 1rem;
  border-radius: 7px;
}
.contacts input.button {
  color: #fff;
  background: #1f95c8;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
  height: 40px;
  width: 97%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}
.contacts input.button:hover{
  background: #045787;
}
.contacts table {
  border-collapse: collapse;
  border-color: #fff;
  text-align: left;
  width: 97%;
}
.row-contact {
  text-align: left;
  border-bottom: 1px solid #cbcbcb;
  height: 40px;
  width: 97%;
  border-radius: 6px;
  cursor: pointer;
}
.single-contact {
  position: absolute;
  right: 10px;
  background-color: #fff;
  width: 48%;
  height: 580px;
  padding: 2rem;
  border-radius: 7px;
  text-align: center;
  display: none;
}
.create-contact {
  position: absolute;
  right: 10px;
  background-color: #fff;
  width: 48%;
  height: 670px;
  padding: 2rem;
  border-radius: 7px;
  text-align: center;
  display: none;
}
.create-contact form input {
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
  height: 50px;
  width: 97%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}
.create-contact form input.button {
  color: #fff;
  background: #1f95c8;
  font-size: 1.2rem;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
  height: 50px;
  width: 97%;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
}

#compose{
  display: none;
}
#sent{
  display: none;
}
#draft{
  display: none;
}
#trash{
  display: none;
}
#contacts{
  display: none;
}
#user-folder{
  display: none;
}
#mail{
  display: none;
}

</style>