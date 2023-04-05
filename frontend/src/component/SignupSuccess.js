import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function SignupSuccess() {
    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card border-0 shadow-lg">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4">
                                회원가입이 완료되었습니다
                            </h1>
                            <div className="text-center">
                                <Link to="/login" className="btn btn-primary btn-lg mx-3">
                                    로그인 하러가기
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SignupSuccess;
