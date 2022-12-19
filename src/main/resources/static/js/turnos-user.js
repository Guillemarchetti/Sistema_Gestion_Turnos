
window.addEventListener('load', function () {

    //Variables globales   
    const select = document.getElementById("selectOd");
    const formTurno = document.querySelector(".formularioTurnos");
    const inputNombre = document.getElementById("inputName");
    const inputApellido = document.getElementById("inputApellido");
    const inputDni = document.getElementById("inputDni");
    const inputFecha = document.getElementById("inputFecha");
    const inputHora = document.getElementById("inputHora");    




    //Select de Odontologos
    function selectOdontologos(odontologos){
       odontologos.forEach(odontologo => {
          let opcion = document.createElement("option");
          text = document.createTextNode(`${odontologo.apellido}, ${odontologo.nombre}`);
          opcion.setAttribute("value", `${odontologo.matricula}`);
          opcion.appendChild(text);       
          select.add(opcion);
       })
    }

    //Funcion para listar odontologos en el select
    function listarOdontologos(){
      const url = '/odontologos/listar';
      const settings = {
         method: 'GET'
      }

      fetch(url, settings)
         .then(response => response.json())
         .then(data => {
                  console.log(data)
                 selectOdontologos(data);
         });
    }
    listarOdontologos();


 
    //Leer los datos del form
    function leerForm(){
      let formData={};
      formData['apellido'] = inputApellido.value;
      formData['nombre'] = inputNombre.value;
      formData['dni'] = inputDni.value;
      formData['fecha'] = inputFecha.value;
      formData['hora'] = inputHora.value;
      formData['odontologo'] = select.value;
      return formData;
    }

    //Resetear el form
    function resetearForm(){
      inputNombre.value = '';
      inputApellido.value = '';
      inputDni.value = '';
      inputFecha.value = '';
      inputHora.value = '';
      select.selectedIndex = 0;
    }


    //Submiteo el form    
    formTurno.addEventListener('submit', function (event) {
      
       event.preventDefault();
       let formData = leerForm();
       crearTurno(formData)
       resetearForm();
    })
    




/*----------------------------------------CREAR TURNO------------------------------------------*/

    function crearTurno(formData) {

        let odontologo= select.options[select.selectedIndex].text.split(",");
        
        let turno = {
        fecha: formData.fecha,
        hora: formData.hora,
        apellidoOd: odontologo[0],
        nombreOd: odontologo[1],
        matricula: formData.odontologo,
        apellidoPac: formData.apellido,
        nombrePac: formData.nombre,
        dni: formData.dni
        }         

        console.log("turno", turno)

        const url = '/turnos/crear';
        const settings = {
        method: 'POST',
        headers: {
                'Content-Type': 'application/json',
        },
        body: JSON.stringify(turno)
        }

        fetch(url, settings)
        .then(response => {        
            if (response.ok)     
            return response.json()              
            
            return response.text()
                .then(text => {throw Error(text)});
        })
        .then(data => {        
            window.alert("Turno creado con exito")
            console.log(data.dni)
            listarTurnos(data.dni);
        })
        .catch(error => {    
            window.alert(error)
        })       

    }




    function renderizarPostIt(turnos){    
        const ul = document.getElementById("ul"); 
        ul.innerHTML = "";        
        turnos.forEach(turno => {
            let fecha = turno.fecha;
            let fechaModificada = moment(fecha).format('DD-MM-YYYY')
            ul.innerHTML += `<li>
                                <a href="#">
                                <h2>Tu turno</h2>
                                <p>Día: ${fechaModificada}</p>
                                <p>Hora: ${turno.hora}</p>
                                <p>Odontologo: ${turno.nombreOd}, ${turno.apellidoOd}</p>
                                <button id=${turno.id} class="borrar">Eliminar</button>
                                </a>
                            </li>`


        })

        
        const botonesBorrar = document.querySelectorAll('.borrar');
        

        botonesBorrar.forEach(boton => {
            boton.addEventListener('click', function(){
                eliminarTurno(this);
            })
        });
    }  


    /*----------------------------------------ELIMINAR ODONTOLOGO------------------------------------------*/
         function eliminarTurno(boton){
            console.log(boton.id) //Me trae el id del turno


             const url = `/Turnos/eliminar/${boton.id}`
             const settings = {
                   method: 'DELETE',
             }
             fetch(url, settings)
                 .then( response => response.status )
                 .then( data => {
                       console.log(data);
                       // Vuelve a recargar las tareas
                       listarTurnos();
                     })
         }









    function listarTurnos(dni){

        const url = `/turnos/buscar/${dni}`;
        const settings = {
        method: 'GET'
        }

        fetch(url, settings)
         .then(response => response.json())
         .then(data => {
            console.log(data)
             renderizarPostIt(data);
         });
    }

    listarTurnos();




})