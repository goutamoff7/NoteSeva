const CustomGauge = ({ value, max }) => {
  const percentage = Math.min(value / max, 1);
  const angle = percentage * 180;

  const getColor = () => {
    if (percentage < 0.33) return "#4ade80"; // Green
    if (percentage < 0.66) return "#f87171"; // Red
    return "#fbbf24"; // Yellow
  };

  return (
    <div className="relative w-48 h-24">
      <svg viewBox="0 0 100 50" className="w-full h-full">
        <path
          d="M10,50 A40,40 0 0,1 90,50"
          fill="none"
          stroke="#1e293b"
          strokeWidth="10"
        />
        <path
          d="M10,50 A40,40 0 0,1 90,50"
          fill="none"
          stroke={getColor()}
          strokeWidth="10"
          strokeDasharray={`${percentage * 126} 126`}
        />
      </svg>
    </div>
  );
};

export default CustomGauge;
