from flask import Flask

from routes import routes

def create_app():
    app = Flask(__name__, instance_relative_config=True)
    with app.app_context():
        app.register_blueprint(routes)
    return app