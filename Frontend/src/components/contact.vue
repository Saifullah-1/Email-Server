<template>
    <tr class="row-contact" @click="singleContact()">
        <td style="width: 50px;text-align: center;"><img src="..\assets\images\contacts-com.svg"></td>
        <th>{{ contactInfo.firstName }} &nbsp; {{ contactInfo.lastName  }}</th>
        <button id="trash-can" style="text-align: center;" @click = "DeleteContact()"><img src="..\assets\images\icons8-trash.svg" ></button>
    </tr>
</template>

<script>
    export default{
        name: 'contact',
        props: ['contactInfo'],
        data(){
            return{
              firstName : this.contactInfo.firstName
             ,lastName :  this.contactInfo.lastName
             ,email: this.contactInfo.email
            }
        }
        ,methods:{
            singleContact(){
                this.emitter.emit('single-contact',{msg1:this.firstName ,msg2: this.lastName ,msg3: this.email[0]})
                console.log(this.firstName)
                console.log(this.lastName)
                console.log(this.email)
            }
            ,DeleteContact(){
                this.emitter.emit('delete-contact',{msg:this.contactInfo.ID})
            }
        }
    }
</script>

<style>
 .row-contact {
  text-align: left;
  border-bottom: 1px solid #cbcbcb;
  height: 40px;
  width: 97%;
  border-radius: 6px;
  cursor: pointer;
}
</style>