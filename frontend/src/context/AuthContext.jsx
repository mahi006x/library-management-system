import { createContext, useContext, useState } from "react";
import api from "../api/axios";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [role, setRole] = useState(localStorage.getItem("role"));
  const [user, setUser] = useState(
    localStorage.getItem("user")
      ? JSON.parse(localStorage.getItem("user"))
      : null
  );

  const login = async (email, password) => {
    const response = await api.post("/auth/login", {
      email,
      password,
    });

    const receivedToken = response.data.token;
    const receivedRole = response.data.role;

    localStorage.setItem("token", receivedToken);
    localStorage.setItem("role", receivedRole);
    localStorage.setItem("user", JSON.stringify({ email }));

    setToken(receivedToken);
    setRole(receivedRole);
    setUser({ email });

    return receivedRole;
  };

  const register = async (name, email, password, role) => {
    const response = await api.post("/auth/register", {
      name,
      email,
      password,
      role,
    });

    const receivedToken = response.data.token;
    const receivedRole = response.data.role;

    localStorage.setItem("token", receivedToken);
    localStorage.setItem("role", receivedRole);
    localStorage.setItem("user", JSON.stringify({ name, email }));

    setToken(receivedToken);
    setRole(receivedRole);
    setUser({ name, email });

    return receivedRole;
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("user");

    setToken(null);
    setRole(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        role,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};