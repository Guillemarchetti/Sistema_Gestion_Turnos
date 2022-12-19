window.addEventListener('load', function () {

    //Variables globales
    const tablaBody = document.getElementById("tablaTurnosBody");
    const select = document.getElementById("selectOd");
    const defaultOption = document.getElementById("default");
    const formTurno = document.querySelector(".formularioTurnos");
    const inputNombre = document.getElementById("inputName");
    const inputApellido = document.getElementById("inputApellido");
    const inputDni = document.getElementById("inputDni");
    const inputFecha = document.getElementById("inputFecha");
    const inputHora = document.getElementById("inputHora");
    const turnoId = document.getElementById("turnoId")
    



    //Boton de editar
    function editarTurno(t){
        selectedRow = t.parentElement.parentElement
        let nombreCompletoPaciente = selectedRow.cells[1].innerHTML.split(",");
        inputNombre.value = nombreCompletoPaciente[1];
        inputApellido.value = nombreCompletoPaciente[0];
        inputDni.value = selectedRow.cells[2].innerHTML;
        inputFecha.value = (selectedRow.cells[3].innerHTML).split('-').reverse().join('-');
        inputHora.value = selectedRow.cells[4].innerHTML;
        select.options[select.selectedIndex].text = selectedRow.cells[5].innerHTML;
    }

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
    let selectedRow= null;
    formTurno.addEventListener('submit', function (event) {
       console.log("selectedRow: ", selectedRow)
       event.preventDefault();
       let formData = leerForm();
       if(selectedRow === null) {
            crearTurno(formData);
        } else{
            actualizarTurno(formData);
        }
        resetearForm();
    })
    
   /* async function buscarPaciente(){
      let dni = inputDni.value;

      const url = `/pacientes/buscar/${dni}`;
      const settings = {
          method: 'GET'
      }

      const response = await fetch(url, settings);
      const data = await response.json();
      console.log(data);
      return data;
    }

    async function buscarOdontologo(){

        let opcionSeleccionada = select.options[select.selectedIndex].value;
        console.log(opcionSeleccionada)

        const url = `/odontologos/buscar/${opcionSeleccionada}`;
        const settings = {
          method: 'GET'
        }

        const response = await fetch(url, settings);
        const data = await response.json();
        console.log(data);
        return data;
    }*/


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
        listarTurnos();
      })
      .catch(error => {    
        window.alert(error)
      })       

   }



    /*----------------------------------------LISTAR TURNOS------------------------------------------*/

    //renderizo la lista
    function renderizarLista(turnos){
        tablaBody.innerHTML = "";        
        turnos.forEach(turno => {
            let fecha = turno.fecha;
            let fechaModificada = moment(fecha).format('DD-MM-YYYY')
            tablaBody.innerHTML += `<tr>
                                        <td id="turnoId" class="celda" style="display:none">${turno.id}</td>
                                        <td id="nombrePaciente" class="celda">${turno.apellidoPac}, ${turno.nombrePac}</td>
                                        <td id="dni" class="celda">${turno.dni}</td>
                                        <td id="fecha" class="celda">${fechaModificada}</td>
                                        <td id="hora" class="celda">${turno.hora}</td>
                                        <td id="nombreOdonto" class="celda">${turno.apellidoOd}, ${turno.nombreOd}</td>
                                        <td id="matricula" class="celda" style="display:none">${turno.matricula}</td>
                                        <td id="botones">
                                            <button class="actualizar">Actualizar</button>
                                            <button id=${turno.id} class="borrar">Eliminar</button>
                                        </td>
                                    </tr>`
        })

        
        const botonesBorrar = document.querySelectorAll('.borrar');
        const botonesActualizar = document.querySelectorAll('.actualizar')
        

        botonesBorrar.forEach(boton => {
          boton.addEventListener('click', function(){
                eliminarTurno(this);
          })
        });

        botonesActualizar.forEach(boton => {
          boton.addEventListener('click', function(){
                editarTurno(this);
          })
        });
        
    }


    //Funcion para listar
        function listarTurnos(){
            const url = '/turnos/listar';
            const settings = {
               method: 'GET'
            }

            fetch(url, settings)
               .then(response => response.json())
               .then(data => {
                        console.log(data)
                       renderizarLista(data);                      
               });
        }



     /*----------------------------------------ELIMINAR TURNO------------------------------------------*/
     function eliminarTurno(t){

         console.log(t)
         let fila =  t.parentElement.parentElement;
         let id = fila.cells[0].innerHTML;
         console.log(id)

         fila.remove();

         const url = `/turnos/eliminar/${id}`
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

   


     /*----------------------------------------ACTUALIZAR TURNO------------------------------------------*/



    function actualizarTurno(formData){

    let odontologo= select.options[select.selectedIndex].text.split(",");
    let matricula = selectedRow.cells[6].innerHTML
    let id = selectedRow.cells[0].innerHTML



       let turnoActualizado = {
         fecha: inputFecha.value,
         hora: inputHora.value,
         apellidoOd: odontologo[0],
         nombreOd: odontologo[1],
         matricula: matricula,
         apellidoPac: formData.apellido,
         nombrePac: formData.nombre,
         dni: formData.dni
       }

       console.log(turnoActualizado)
  
      const url = `/turnos/actualizar/${id}`
      const settings = {
        method: 'PATCH',
        headers: {          
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(turnoActualizado)
      }
      fetch(url, settings)
      .then( response => response.json() )
      .then( data => {
        console.log(data);
        // Vuelve a recargar las tareas
        listarTurnos();
      })
      selectedRow = null;
  }
     
  
  listarTurnos();

})

 
    
  








