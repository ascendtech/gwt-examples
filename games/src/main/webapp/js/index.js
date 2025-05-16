import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'

import VueRouter from 'vue-router';

import './polyfill';
import '../css/todo.css';
import vue2Dropzone from 'vue2-dropzone';
import 'vue2-dropzone/dist/vue2Dropzone.min.css';
import '../../../../node_modules/ag-grid-community/styles/ag-grid.css';
import '../../../../node_modules/ag-grid-community/styles/ag-theme-material.css';
import '../../../../node_modules/ag-grid-community/styles/ag-theme-alpine.css';
import {AgGridVue} from 'ag-grid-vue';
import Highcharts from 'highcharts';
import 'es7-object-polyfill';
import moment from 'moment';

Vue.use(Vuetify);

Vue.use(VueRouter);

Vue.component("AgGridVue", AgGridVue);
Vue.component("vueDropzone", vue2Dropzone);

window.Vue = Vue;
window.Vuetify = Vuetify;
window.VueRouter = VueRouter;
window.Highcharts = Highcharts;
window.Moment = moment;


