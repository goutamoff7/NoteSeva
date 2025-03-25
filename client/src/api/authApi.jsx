import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { useAppContext } from "../context/AppContext";

const fetchUser = async () => {
  const {backendUrl} = useAppContext()
  
  const { data } = await axios.get(`${backendUrl}/oauth2`);
  
  return data;
};

export const useUser = () => {
  return useQuery({
    queryKey: ["user"],
    queryFn: fetchUser,
  });
};
