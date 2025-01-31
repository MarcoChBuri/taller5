from flask import Blueprint, json, render_template, request, redirect, url_for, flash, session
import requests

routes = Blueprint('routes', __name__)

URL = "http://localhost:8000/myapp/"

@routes.route('/')
def home():
    r = requests.get(URL + "Estacion/all")
    data = r.json().get('data')
    return render_template('index.html', estaciones=data)

@routes.route('/findRoute/<algoritmo>/<origen>/<destino>')
def findRoute(algoritmo, origen, destino):
    url = URL + f'grafo/minPath/{algoritmo}/{origen}/{destino}'

    r = requests.get(url)
    data = r.json().get('data')
    
    return render_template('findRoute.html')

@routes.route('/estation/save', methods=['POST'])
def saveestation():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"motivo": form["motivo"],
                "observaciones": form["observaciones"],
                "motivo": form["motivo"],
                "turnoId": int(form["turnoId"])}
    r = requests.post(URL + 'EStacion/save', data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha guardado correctamente', category='info')
    else:
        flash('No se ha podido guardar', category='error')
    return redirect('/estation/add') 

@routes.route('/conection/save', methods=['POST'])
def saveconection():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"estacionOrigen": form["estacionOrigen"],
                "estacionDestino": form["estacionDestino"],
                "distancia": form["distancia"]}
    r = requests.post(URL + 'conexion/save', data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha guardado correctamente', category='info')
    else:
        flash('No se ha podido guardar', category='error')
    return redirect('/conection/add')   