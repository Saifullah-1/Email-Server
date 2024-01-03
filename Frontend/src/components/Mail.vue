<template>
    <div>
        <input type="checkbox" style="margin-left: 20px;vertical-align: middle;" @click="selected()">
        <input class="star" type="checkbox" checked @click ="star()" >
        <label for="star" style="margin-left: 10px;">{{mail.senderName}}</label>
        <p @click="MailContent()">{{mail.subject}}</p>
    </div>
</template>

<script>
    export default{
        name: 'Mail',
        props: ['mail'],
        data(){
            return{
                senderName : this.mail.senderName
                ,subject : this.mail.subject
                ,currentMail: this.mail
                ,url:'http://localhost:8080/mail'
                ,check:false
                ,favourite: this.mail.favourite
            }
        }
        ,methods:{
            MailContent(){
                this.emitter.emit('mail-content',{msg1:this.mail.from,msg2:this.mail.subject,msg3:this.mail.body})
            }
            ,async star(){
                this.emitter.emit("starred",{msg:this.mail.ID})
            },
            async selected(){
                this.check = !this.check
                if(this.check){
                    this.emitter.emit("mail-selected",{msg : this.mail.ID})
                }
            }
        }
    }
</script>
<style>

</style>