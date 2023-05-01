import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import {Alert} from "react-bootstrap";

function Delete() {
    const navigate = useNavigate();

    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [notMatchError, setNotMatchError] = useState('');

    useEffect(() => {
        axios
            .get('http://localhost:8080/member', {
                headers: {
                    Authorization: localStorage.getItem('Authorization'),
                },
            })
            .then(response => {
            })
            .catch(error => {
                navigate("/");
            });
    }, []);

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handlePasswordCheckChange = (event) => {
        setPasswordCheck(event.target.value);
    };

    const [showAlert, setShowAlert] = useState(false);

    const handleDeleteClick = () => {
        axios.post('http://localhost:8080/withdrawal', { password, passwordCheck }, {
            headers: {
                Authorization: localStorage.getItem('Authorization'),
            },
        })
            .then((response) => {
                setShowAlert(true);
                setTimeout(() => {
                    navigate("/");
                }, 1700);
            })
            .catch((error) => {
                if (error.response.data.message) {
                    setNotMatchError(error.response.data.message);
                }
            });
    };

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4" style={{ color: 'navy' }}>회원 탈퇴</h1>
                            <form>
                                <div className="form-group">
                                    <label htmlFor="password">비밀번호</label>
                                    <input type="password" className="form-control" id="password" value={password}
                                           onChange={handlePasswordChange} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="passwordCheck">비밀번호 확인</label>
                                    <input type="password" className="form-control" id="passwordCheck" value={passwordCheck}
                                           onChange={handlePasswordCheckChange} />
                                </div>

                                {notMatchError && (
                                    <div className="alert alert-danger custom-alert" role="alert">
                                        {notMatchError}
                                    </div>
                                )}

                                <div className="d-flex text-center mt-4">
                                    <button type="button" className="btn btn-danger"
                                            onClick={handleDeleteClick}>회원 탈퇴</button>
                                    <Link to="/main" className="btn btn-success mx-3">
                                        홈으로
                                    </Link>
                                </div>
                            </form>
                            <Alert variant="success" show={showAlert}
                                   onClose={() => setShowAlert(false)} dismissible>
                                회원탈퇴가 되었습니다.
                            </Alert>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Delete;
