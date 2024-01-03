<template>
    <div class="bar">
          <label for="sort">Sort mail:</label>
          <select name="Sort" id="sort" v-model="sortby">
            <option value="Date" @click="sort()">Date</option>
            <option value="Priority" @click="sort()">Starred</option>
            <option value="Sender" @click="sort()">Sender</option>
            <option value="Subject" @click="sort()">Subject</option>
            <option value="Attachments" @click="sort()">Attachments</option>
          </select>
          <div class="search-filter">
            <button id="search" style="text-align: center;" @click ="search()"><img src="..\assets\images\icons8-search.svg"></button>
            <input type="search" placeholder="Search" v-model = "searchText" >
            <div class="dropdown">
              <button id="filter" style="text-align: center;" @click="showFilterDraft()"><img src="..\assets\images\filter.svg"></button>
              <div class="dropdown-content" id="search-draft">
                <form>
                  <label for="from" style="font-size: 17px; margin-left: 10px;margin-right: 20px;">From:</label>
                  <input type="text" id="from" placeholder="From" v-model="filterFrom">
                </form>
                <form>
                  <label for="subject" style="font-size: 17px; margin-left: 10px;">Subject:</label>
                  <input type="text" id="subject" placeholder="Subject" v-model ="filterSubject">
                </form>
                <button id="start-filter" @click="filter()">Filter</button>
              </div>
            </div>
          </div>
          <button id="refresh" style="text-align: center;"><img src="..\assets\images\icons8-refresh.svg"></button>
          <button id="trash-can" style="text-align: center;" @click = "Delete()"><img src="..\assets\images\icons8-trash.svg" ></button>
        </div>
</template>

<script >
export default {
    data(){
      return{
        sortby: '',
        searchText:'',
        filterFrom:'',
        filterSubject:'',
        selected: []
      }
    }
    ,methods: {
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
        ,showFilterDraft() {
            var x = document.getElementById("search-draft")
            x.style.display = "block"
        }
        ,hideFilterDraft() {
            var x = document.getElementById("search-draft")
            x.style.display = 'none'
        }
        ,sort(){
          this.emitter.emit("sort",{msg : this.sortby})
          this.sortby = ''
        }
        ,search(){
          this.emitter.emit("search",{msg : this.searchText})
          this.searchText = ''
        }
        ,filter(){
          this.hideFilterDraft()
          if( this.filterFrom != '' || this.filterSubject != ''){
            this.emitter.emit("filter",{msg1: this.filterFrom , msg2: this.filterSubject})
          }
        }
        ,Delete(){
          this.emitter.emit("delete",{msg:this.selected})
          this.selected=[]
        }
    }
    ,mounted(){
        this.emitter.on("mail-selected",(data)=>{
          this.selected.push(data.msg)
        })
    }
}
</script>

<style>
.bar {
  height: 35px;
  position: relative;
}
.bar select{
  margin-left: 10px;
  width: 150px;
  height: 35px;
  border-radius: 5px;
  background-color: #1f95c8;
  color: #fff;
}
.search-filter {
  float: right;
  margin-right: 370px;
  display: flex;
  padding-left: 7px;
  height: 35px;
  width: 600px;
  font-size: 17px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  position: relative;
}
.bar div input {
  border: none;
  background: transparent;
  margin: 0;
  width: 380px;
  padding: 7px 8px;
  font-size: 14px;
  color: inherit;
  border: 1px solid transparent;
  border-radius: inherit;
  outline: none;
}
#search {
  border: none;
  background: transparent;
  margin: 0;
  padding-top: 5px;
  font-size: 14px;
  color: inherit;
  border: 1px solid transparent;
  border-radius: inherit;
}
#filter {
  position: absolute;
  right: 0px;
  border: none;
  background: transparent;
  margin: 0;
  padding: 0px;
  font-size: 14px;
  color: inherit;
  border: 1px solid transparent;
  border-radius: inherit;
}
.dropdown {
  position: absolute;
  right: 0px;
  margin-right: 0px;
  border: none;
  background: transparent;
  margin: 0;
  margin-right: 0;
  width: 35px;
  color: inherit;
  border: 1px solid transparent;
  border-radius: inherit;
  outline: none;
}
.dropdown-content{
  padding-top: 20px;
  width: 600px;
  height: 265px;
  border-radius: 7px;
  display: none;
  z-index: 5;
  position: absolute;
  right: 0px;
  top: 35px;
  background-color: #d6f1f5;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
#filter:hover{
  background-color: #1f95c8;
}
.dropdown-content form input {
  height: 40px;
  width: 500px;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  background-color: #fff;
}
#start-filter {
  position: absolute;
  right: 20px;
  bottom: 0px;
  height: 40px;
  width: 100px;
  padding: 0 15px;
  font-size: 17px;
  margin-bottom: 1.3rem;
  border: 1px solid transparent;
  border-radius: 6px;
  outline: none;
  color: #fff;
  background-color: #1f95c8;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
#start-filter:hover {
  background: #045787;
}
#refresh {
  background-color: #1f95c8;
  position: absolute;
  padding-top: 3px;
  right: 50px;
  align-items: center;
  height: 35px;
  width: 35px;
  border: 1px solid #ddd;
  border-radius: 4px;
  outline: none;
  color: #fff;
  background: #1f95c8;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
#refresh:hover {
  background: #045787;
}
#trash-can{
  background-color: #1f95c8;
  position: absolute;
  right: 0px;
  align-items: center;
  height: 35px;
  width: 35px;
  border: 1px solid #ddd;
  border-radius: 4px;
  outline: none;
  color: #fff;
  background: #1f95c8;
  font-weight: 500;
  letter-spacing: 1px;
  cursor: pointer;
  transition: 0.4s;
}
#trash-can:hover {
  background: #045787;
}
</style>