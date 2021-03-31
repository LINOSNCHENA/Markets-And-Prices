<template>
  <div class="box" style="text-align: center">
    <div class="container" style="text-align: left">
      <h2>
        <span>{{ nameTop }}</span>
      </h2>

      <div>
        <h3>{{ nameDown }}</h3>
        <vuejs-datepicker
          format="dd-MM-yyyy"
          placeholder="select a day for your report"
          clear-button-icon="true"
          v-model="dateChoice.date"
          :disabled-dates="state.disabledDates"
          lang="en"
          @selected="selectMarketDay"
        ></vuejs-datepicker>
    
      <h6>
        Intra-Day Market Results: {{ dateChoice.date.toLocaleDateString() }}
      </h6>
 
      <section id="chart-One">
        <chart v-if="loaded" :chart-data="marketData"> </chart>
      </section>
 </div>
      <section id="chart-Two">
        <table
          class="table is-bordered is-striped is-narrow is-hoverable table is-bordered"
        >
          <thead>
            <tr class="is-selected">
              <th>Hour</th>
              <th>Traded Volumes (MWh)</th>
              <th>Weighted Average of the Prices (EUR/MWh)</th>
              <th>Minimal Price(EUR/MWh)</th>
              <th>Maximal Price (EUR/MWh)</th>
              <th>Last Price(EUR/MWh)</th>
            </tr>
          </thead>
          <tfoot>
            <tr>
              <th>Hour</th>
              <th>Traded Volumes (MWh)</th>
              <th>Weighted Average of the Prices (EUR/MWh)</th>
              <th>Minimal Price(EUR/MWh)</th>
              <th>Maximal Price (EUR/MWh)</th>
              <th>Last Price(EUR/MWh)</th>
            </tr>
          </tfoot>
          <tbody>
            <tr v-for="(value, i) in marketData" :key="i">
              <td>{{ value.hour }}</td>
              <td>{{ value.tradedvolume }}</td>
              <td>{{ value.averageprice }}</td>
              <td>{{ value.minimumprice }}</td>
              <td>{{ value.maximumprice }}</td>
              <td>{{ value.lastprice }}</td>
            </tr>
          </tbody>
        </table>
      </section>
    </div>
  </div>
</template>

<script>
import vuejsDatepicker from "vuejs-datepicker";
import axios from "axios";
import Chart from "./Chart";

export default {
  name: "prices",
  components: {
    vuejsDatepicker,
    Chart,
  },
  data() {
    return {
      nameTop: "Short-Term-Markets",
      nameDown: "Intra-Day Market",
      errors: [],
      marketData: [],
      dateNow: new Date().getUTCDate(),
      dateChoice: { date: new Date() },
      loaded: false,
      state: {
        disabledDates: {
          to: new Date(2020, 9, 15),
          from: new Date(),
        },
      },
    };
  },
  mounted() {
    this.selectMarketDay();
  },

  methods: {
    selectMarketDay() {
      this.loaded = false;
      this.marketData = "";
      let date = { sortDate: this.dateChoice.date };
      const transactionDate = new Date(date.sortDate);
      const year = transactionDate.getFullYear();
      const month = 1 + transactionDate.getMonth();
      const day = transactionDate.getDate();

      if (transactionDate > new Date()) {
        console.log("Bad selection");
      } else {
        console.log("Good selection");
        this.axios
          .post(
            "http://localhost:8040/api/markets/updatedatabase/" +
              year +
              "/" +
              month +
              "/" +
              day +
              ""
          )
          .then((res) => {
            console.log("=====|data-fetch-started|=======");
            console.log(year + "|" + month + "|" + day);
            axios
              .get("http://localhost:8040/api/markets/listed")
              .then((response) => {
                this.marketData = response.data;
                this.loaded = true;
                console.log(response.data);
                console.log("=====|data-fetch-finished|=======");
              });
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
          });
      }
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
