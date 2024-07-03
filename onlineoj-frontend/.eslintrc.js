// module.exports = {
//   root: true,
//   env: {
//     node: true,
//   },
//   extends: [
//     "plugin:vue/vue3-essential",
//     "eslint:recommended",
//     "@vue/typescript/recommended",
//     "plugin:prettier/recommended",
//   ],
//   parserOptions: {
//     ecmaVersion: 2020,
//   },
//   rules: {
//     "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
//     "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
//   },
// };


module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    "plugin:vue/vue3-essential",
    "eslint:recommended",
    "@vue/typescript/recommended",
    "plugin:prettier/recommended",
  ],
  parserOptions: {
    ecmaVersion: 2020,
  },
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    "prettier/prettier": "off"
    // 禁用prettier对换行符和分号的检查
    // "prettier/prettier": [
    //   "error",
    //   {
    //     endOfLine: "auto",
    //     semi: false,
    //   },
    // ],
  },
};

