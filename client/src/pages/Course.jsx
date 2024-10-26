import Card from "../components/Card";
import Footer from "../components/Footer";

const courses = [
  { title: "B.Tech", para: "Technological innovation starts here", image: "btech icon.png" },
  { title: "B.Pharm", para: "Gateway to pharmaceutical innovation.", image: "bpharm icon.png" },
  { title: "MBBS", para: "Medicine's future starts here.", image: "mbbs icon.png" },
  { title: "Diploma", para: "Gateway to specialized skills & opportunities.", image: "diploma icon.png" },
  { title: "Nursing", para: "Compassionate care begins here.", image: "nursing icon.png" },
  { title: "B.Arch", para: "Creativity shapes spaces here.", image: "barch icon.png" },
];

const Course = () => {
  return (
    <div className="bg-darkbg max-w-full flex flex-col">
      {/* Course cards in a 4-column grid */}
      <div className="my-[80px] max-w-[80%] pl-5 flex flex-wrap justify-center gap-12 mx-auto">
        {courses.map((course, index) => (
          <Card
            key={index} 
            title={course.title}
            image={course.image}
            para={course.para}
          />
        ))}
      </div>



      <Footer />
    </div>
  );
};

export default Course;
