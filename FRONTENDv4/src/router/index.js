import Vue from "vue";
import Router from "vue-router";
import Prices from "@/components/Prices";

Vue.use(Router);

export default new Router({
  routes: [
 
    {
      path: "/",
      name: "Prices",
      component: Prices,
    }
  
  ],
});
