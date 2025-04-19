import React from "react";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
} from "chart.js";

ChartJS.register(LineElement, PointElement, LinearScale, CategoryScale);

const LikeGains = () => {
  const data = {
    labels: ["", "", "", "", "", "", ""],
    datasets: [
      {
        data: [80, 100, 75, 120, 90, 110, 100, 120],
        borderColor: "#21B573",
        borderWidth: 2,
        fill: true,
        backgroundColor: "rgba(34,197,94,0.1)",
        tension: 0.4,
        pointRadius: 0,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: { legend: { display: false } },
    scales: {
      x: { display: false },
      y: { display: false },
    },
  };

  return (
    <div className="bg-[#1f2937] p-2 rounded-2xl w-54 h-52  shadow-md text-white flex flex-col justify-between items-center border border-gray-100">
      <div className="flex items-center flex-col mb-2">
        <div className="flex items-center gap-2 w-full">
          <span className="text-2xl">🤍</span>
          <h2 className="text-2xl font-semibold">Likes Gained</h2>
        </div>
        <div className="flex items-center justify-between w-full px-2">
          <p className="text-gray-400 text-sm font-semibold">152K</p>
          <span className="text-green-400 text-sm font-semibold">+22%</span>
        </div>
      </div>
      <div className="w-full h-1/2">
        <Line data={data} options={options} height={200} />
      </div>
    </div>
  );
};

export default LikeGains;
