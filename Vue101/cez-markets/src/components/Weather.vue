// Weather.vue

<template>
  <div>
    <h1>Cez Weather App</h1>
    <button v-on:click="getWeatherData">Get Praha Weather Data</button>
    <div
      v-for="weatherData in weatherDataList"
      :key="weatherData.id"
      class="weather-data"
    >
      <div class="weather-stats">
        <div>
          <span>{{ weatherData.time }}</span>
        </div>
        <div>
          <span class="location">{{ weatherData.location }}</span>
        </div>
      </div>
      <div class="weather-icon">
        <img
          :src="`https://www.metaweather.com/static/img/weather/${weatherData.abbr}.svg`"
        />
      </div>
      <div class="weather-temp">
        <span>{{ weatherData.temp }}°</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Weather",
  data() {
    return {
      weatherDataList: [],
    };
  },
  methods: {
    getWeatherData() {
      fetch("fakedatabase.json")
        .then((response) => response.json())
        .then((data) => (this.weatherDataList = data));
    },
  },
};
</script>


// Weather.json

<style scoped>
.weather-data {
  display: flex;
  align-items: center;
  margin-top: 20px;
  margin-left: 20px;
  border-bottom: 2px solid #ccc;
  padding: 10px;
}

.weather-icon {
  flex-grow: 1;
}

.weather-stats {
  flex-grow: 8;
  text-align: left;
  padding-left: 20px;
}

.weather-stats .location {
  font-size: 20px;
}

.weather-temp {
  flex-grow: 1;
  font-size: 35px;
}

img {
  width: 50px;
}

button {
  padding: 10px;
  background-color: blue;
  color: white;
  border: 1px solid #ccc;
}
</style>