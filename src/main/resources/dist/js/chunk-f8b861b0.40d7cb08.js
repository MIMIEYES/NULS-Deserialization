(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f8b861b0"],{1959:function(s,t,a){"use strict";a.d(t,"a",(function(){return e}));var c=a("9f30");a("6ace");async function e(s){return await Object(c["a"])("/","getAccount",[s]).then(s=>{return s.hasOwnProperty("result")?{success:!0,data:s.result}:{success:!1,data:s}}).catch(s=>{return{success:!1,data:s}})}},"231d":function(s,t,a){"use strict";var c=a("c310"),e=a.n(c);e.a},9419:function(s,t,a){"use strict";a.r(t);var c=function(){var s=this,t=s.$createElement,a=s._self._c||t;return a("div",{staticClass:"account w1200"},[a("div",{staticClass:"address"},[a("span",{staticClass:"font16"},[s._v(s._s(s.$t("public.address"))+": "+s._s(s.accountInfo.address))]),a("font",{staticClass:"fr font16"},[s._v(s._s(s.$t("public.balance"))+": "+s._s(s.accountInfo.balance)),a("em",{staticClass:"fCN"},[s._v(" NULS")])])],1),a("Password",{ref:"password",on:{passwordSubmit:s.passSubmit}})],1)},e=[],n=a("d1f0"),r=a("6ace"),o=a("1959"),u={data(){return{accountInfo:JSON.parse(localStorage.getItem("accountInfo"))}},created(){this.addressInfoByAddress(this.accountInfo.address)},mounted(){this.userSetInterval=setInterval(()=>{this.addressInfoByAddress(this.accountInfo.address)},1e4)},destroyed(){clearInterval(this.userSetInterval)},components:{Password:n["a"]},methods:{async addressInfoByAddress(s){let t={aesPri:this.accountInfo.aesPri,pub:this.accountInfo.pub},a=await Object(o["a"])(s);a.success?(this.accountInfo={},a.data.balance=Object(r["e"])(a.data.balance),this.accountInfo={...t,...a.data}):this.accountInfo.balance=0},toUrl(s,t,a){let c=Object(r["c"])(s,t,a);c.success&&this.$router.push(c.data)}}},d=u,i=(a("231d"),a("2877")),l=Object(i["a"])(d,c,e,!1,null,null,null);t["default"]=l.exports},c310:function(s,t,a){}}]);