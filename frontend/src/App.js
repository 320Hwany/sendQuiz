import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './component/Signup';
import Login from "./component/Login";
import Home from "./component/Home";
import Main from "./component/Main";
import SignupSuccess from "./component/SignupSuccess";
import Setting from "./component/Setting";
import Quiz from "./component/Quiz";
import Manage from "./component/Manage";
import Delete from "./component/Delete";

function App() {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/main" element={<Main />} />
                    <Route path="/setting" element={<Setting />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/signup/success" element={<SignupSuccess />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/hwany/quiz" element={<Quiz />} />
                    <Route path="/manage" element={<Manage />} />
                    <Route path="/delete" element={<Delete />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
