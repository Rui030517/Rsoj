import { createStore } from "vuex";
import user from "./user";
import theme from "./theme";
import auth from "./auth";
import questionDrawer from "./addQuestion";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    user,
    theme,
    auth,
    questionDrawer,
  },
});
