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
        text_blue2: "rgba(0, 25, 67, 1)",
        white_1: "rgba(226, 232, 240, 1)",
        white_2: "rgba(138, 138, 138, 1)",

        tech_bg: "rgba(73, 187, 189, 0.3)",
        text_green:
          " linear-gradient(180deg, #21B573 0%, #21B573 16.06%, #21B573 86.9%, #21B573 106.25%)",

        iconColor: "rgba(108, 108, 131, 1)",
        text_blue: "rgba(5, 11, 32, 1)",
        name_color: "rgba(47, 50, 125, 1)",
      },

      fontFamily: {
        poppins: ["Poppins", "sans-serif"],
      },
      backgroundImage: {
        backGroundImg: "url('project-frame1.png')",
      },
    },
  },

  plugins: [],
};
