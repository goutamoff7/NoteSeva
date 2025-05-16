import { motion } from "framer-motion";

const Loader = () => {
  return (
    <div className=" h-screen w-full flex justify-center items-center bg-white overflow-hidden flex-col ">
      {/* Image moves from center to up */}
      <motion.img
        src="/loader.svg"
        alt="loader"
        className="h-[200px] w-[200px] "
        initial={{ opacity: 0, scale: 0.1 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ delay: 1.1, duration: 1.6, ease: "easeOut" }}
      />

      {/* Text fades and scales in slightly after image movement */}

      <motion.span
        initial={{ y: 150, opacity: 0 }} // Start below
        animate={{ y: 0, opacity: 1 }} // Slide up and fade in
        transition={{
          delay: 1.2,
          type: "spring",
          stiffness: 100,
          damping: 10,
        }}
        className="font-loopet font-semibold text-4xl"
      >
        Noteseva
      </motion.span>
    </div>
  );
};

export default Loader;
