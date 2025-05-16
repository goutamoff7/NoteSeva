import GaugeChart from "react-gauge-chart";

const UploadSection = ({ userInfo }) => {
  const categories = [
    {
      name: "Notes",
      count: userInfo.notes.length,
      color: "border-[#21B573]",
      backgroundColor: "bg-[#21B573]",
      icon: "icon/carbon--book.svg",
    },
    {
      name: "organizer",
      count: userInfo.organizer.length,
      color: "border-[#FAAF3A]",
      backgroundColor: "bg-[#FAAF3A]",
      icon: "icon/clarity--book-line.svg",
    },
    {
      name: "PYQ",
      count: userInfo.pyq.length,
      color: "border-[#DF615C]",
      backgroundColor: "bg-[#DF615C]",
      icon: "icon/quill--paper.svg",
    },
  ];

  const totalUploads =
    userInfo.organizer.length + userInfo.pyq.length + userInfo.notes.length;
  const arcsLength = categories.map((item) => item.count / totalUploads);

  return (
    <div className="bg-[#0c1b2a] text-white p-6 rounded-2xl w-full max-w-xl border border-white flex justify-between items-center shadow-xl">
      <div className="w-2/3 ">
        <h2 className="text-lg font-semibold underline decoration-white mb-2">
          Total Uploads
        </h2>
        <GaugeChart
          id="gauge-chart"
          nrOfLevels={3}
          colors={["#4ade80", "#f87171", "#fbbf24"]}
          arcWidth={0.3}
          arcsLength={arcsLength}
          textColor="#ffffff00"
          needleColor="#ffffff00"
          needleBaseColor="#ffffff00"
          arcPadding={0.02}
          hideNeedle={true}
        />
        <p className="text-3xl font-bold mt-[-20px] text-center">
          {totalUploads}
        </p>
        <p className="text-sm underline decoration-white text-center">
          Uploads
        </p>
      </div>

      <div className="border border-gray-500 rounded-xl p-4">
        {categories.map((cat, idx) => (
          <div key={idx} className="flex items-center space-x-2 mb-3">
            <div
              className={`w-12 h-12 rounded-md border-2  ${cat.color} bg-opacity-20 ${cat.backgroundColor} flex items-center justify-center text-lg`}
            >
              <img src={cat.icon} className="opacity-100" alt="" />
            </div>
            <div>
              <p className="text-sm">{cat.name}</p>
              <p className="text-xs text-gray-300">{cat.count}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UploadSection;
