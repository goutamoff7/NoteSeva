import Card from "../components/Card";

const courses = [
  {
    title: "B.Tech",
    para: "Something short and simple here",
    image: "btech.png",
  },
  {
    title: "B.Pharm",
    para: "Something short and simple here",
    image: "btech.png",
  },
  {
    title: "MBBS",
    para: "Something short and simple here",
    image: "btech.png",
  },
  {
    title: "Diploma",
    para: "Something short and simple here",
    image: "btech.png",
  },
  {
    title: "Nursing",
    para: "Something short and simple here",
    image: "btech.png",
  },
  {
    title: "B.Arch",
    para: "Something short and simple here",
    image: "btech.png",
  },
];

const Course = () => {
  return (
    <div className="bg-darkbg max-w-full min-h-screen flex flex-col justify-center">
      {/* first row card */}

      <div className=" mx-auto max-w-6xl  pt-11 pl-5 grid grid-cols-4 gap-12 ">
        {courses.slice(0, 4).map((course, index) => (
          <Card
            key={index}
            title={course.title}
            image={course.image}
            para={course.para}
          />
        ))}
      </div>

      {/* second row card */}

      <div className=" mx-auto max-w-6xl  pt-11 pl-5 grid grid-cols-2 gap-12 justify-center ">
        {courses.slice(4).map((course, index) => (
          <Card
            key={index}
            title={course.title}
            image={course.image}
            para={course.para}
          />
        ))}
      </div>
    </div>
  );
};

export default Course;
