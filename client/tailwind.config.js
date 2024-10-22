module.exports = {
  content: [
    "./index.html", // Include index.html for Vite
    "./src/**/*.{js,ts,jsx,tsx}", // Include all src files
  ],
  theme: {
    extend: {
      colors: {
        darkbg: "rgba(30, 41, 59, 1)",
        btngreen: " rgba(33, 175, 133, 1)",
        darkblack: "rgba(0, 6, 15, 1)",
        navcol: "var(--Neutral-n200, rgba(226, 232, 240, 1))",
        whitee: "rgba(255, 255, 255, 1)",
        darkblue: "rgba(50, 46, 105, 1)",
        midblue: "#0056E7",
      },
      fontFamily: {
        poppins: ["Poppins", "sans-serif"],
      },
    },
  },

  plugins: [],
};
