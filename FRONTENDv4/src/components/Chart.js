import { Line, mixins } from "vue-chartjs";

export default {
  extends: Line,
  props: {
    chartData: {
      type: Array | Object,
      required: false,
    },
    chartLabels: {
      type: Array,
      required: false,
    },
  },
  mixins: [mixins.reactiveProp],
  data() {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,

        scales: {
          xAxes: [
            {
              display: true,
              fontSize: 15,
              ticks: {
                fontSize: 15,
              },
              scaleLabel: {
                display: true,
                labelString: "Hour",
                fontSize: 15,
              },
            },
          ],
          yAxes: [
            {
              position: "left",
              display: true,             
              fontSize: 15, //
              ticks: {
                fontSize: 15,
              },
              scaleLabel: {
                display: true,
                labelString: "Volume (Eur/MVh)",
                fontSize: 15,
              },
            },
          ],
        },
      },
    };
  },

  mounted() {
    this.renderChart(
      {
        labels: this.chartData.map((entry) => (entry.hour, entry.hour)),
        datasets: [
          {
            type: "bar",
            label: "Traded Volume",
            borderColor: "DeepSkyBlue",
            pointBackgroundColor: "DeepSkyBlue",
            borderWidth: 1,
            pointBorderColor: "DeepSkyBlue",
            backgroundColor: "blue",
            pointRadius: 1,
            lineTension: 0,
            data: this.chartData.map((entry) => entry.tradedvolume / 10),
          },
          {
            label: "Average Price",
            borderColor: "Blue",
            pointBackgroundColor: "transparent",
            borderWidth: 1,
            pointBorderColor: "White",
            backgroundColor: "transparent",
            pointRadius: 1,
            lineTension: 0,
            data: this.chartData.map((entry) => entry.averageprice),
          },
          {
            label: "Minimum Price",
            borderColor: "Yellow",
            pointBackgroundColor: "transparent",
            borderWidth: 2,
            pointBorderColor: "White",
            backgroundColor: "transparent",
            pointRadius: 3,
            lineTension: 0,
            data: this.chartData.map((entry) => entry.minimumprice),
          },
          {
            label: "Maximum Price",
            borderColor: "Red",
            pointBackgroundColor: "transparent",
            borderWidth: 2,
            pointBorderColor: "white", // Inside
            backgroundColor: "transparent",
            pointRadius: 3,
            lineTension: 0,
            data: this.chartData.map((entry) => entry.maximumprice),
          },
        ],
      },
      this.options
    );
  },
};
