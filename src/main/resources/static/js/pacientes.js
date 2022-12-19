window.addEventListener('load', function () {

    //Variables globales
    const tablaBody = document.getElementById("tablaPacientesBody");
    const formPaciente = document.querySelector(".formularioPacientes");
    const inputNombre = document.getElementById("inputName");
    const inputApellido = document.getElementById("inputApellido");
    const inputDni = document.getElementById("inputDni");
    const inputFecha = document.getElementById("inputFecha");
    const inputDomicilio = document.getElementById("inputDomicilio");


    //Boton de editar
    function editarPaciente(p){
        selectedRow = p.parentElement.parentElement
        inputNombre.value = selectedRow.cells[0].innerHTML;
        inputApellido.value = selectedRow.cells[1].innerHTML;
        inputDni.value = selectedRow.cells[2].innerHTML;
        inputFecha.value = (selectedRow.cells[3].innerHTML).split('-').reverse().join('-');
        inputDomicilio.value = selectedRow.cells[4].innerHTML;

        //moment(selectedRow.cells[3].innerHTML, moment.defaultFormat).toDate()

    }



    //Leer los datos del form
    function leerForm(){
      let formData={};
      formData['apellido'] = inputApellido.value;
      formData['nombre'] = inputNombre.value;
      formData['dni'] = inputDni.value;
      formData['fechaAlta'] = inputFecha.value;
      formData['domicilio'] = inputDomicilio.value;
      return formData;
    }

    //Resetear el form
    function resetearForm(){
      inputNombre.value = '';
      inputApellido.value = '';
      inputDni.value = '';
      inputFecha.value = '';
      inputDomicilio.value = '';
    }


    //Submiteo el form
    let selectedRow= null;
    formPaciente.addEventListener('submit', function (event) {
       console.log("selectedRow: ", selectedRow)
       event.preventDefault();
       let formData = leerForm();       
       if(selectedRow === null) {
            crearPaciente();
        } else{
            actualizarPaciente(formData);
        }
        resetearForm();
    })
    



/*----------------------------------------CREAR PACIENTE------------------------------------------*/

   function crearPaciente() {
                
    console.log("creando paciente dni: ", inputDni.value)


    let paciente = {
      apellido: inputApellido.value,
      nombre: inputNombre.value,
      dni: inputDni.value,
      fechaAlta: inputFecha.value,
      domicilio: inputDomicilio.value
    }         

    const url = '/pacientes/crear';
    const settings = {
      method: 'POST',
      headers: {
            'Content-Type': 'application/json',
      },
      body: JSON.stringify(paciente)
    }

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        window.alert("Paciente creado con exito")
        listarPacientes();
    })
      .catch(error => {
        window.alert("No se pudo crear el paciente")
    })       

   }



    /*----------------------------------------LISTAR PACIENTES------------------------------------------*/

    //renderizo la lista
    function renderizarLista(pacientes){
        tablaBody.innerHTML = "";
        
        pacientes.forEach(paciente => {
            let fecha = paciente.fechaAlta;
            let fechaModificada = moment(fecha).format('DD-MM-YYYY')
            tablaBody.innerHTML += `<tr>
                                        <td id="nombre" class="celda">${paciente.nombre}</td>
                                        <td id="apellido" class="celda">${paciente.apellido}</td>
                                        <td id="dni" class="celda">${paciente.dni}</td>
                                        <td id="fechaAlta" class="celda">${fechaModificada}</td>
                                        <td id="domicilio" class="celda">${paciente.domicilio}</td>
                                        <td id="botones">
                                            <button id=${paciente.dni} class="actualizar">Actualizar</button>
                                            <button id=${paciente.dni} class="borrar">Eliminar</button>
                                        </td>
                                    </tr>`
        })

        
        const botonesBorrar = document.querySelectorAll('.borrar');
        const botonesActualizar = document.querySelectorAll('.actualizar')
        

        botonesBorrar.forEach(boton => {
          boton.addEventListener('click', function(event){
                eliminarPaciente(event.target);
          })
        });

        botonesActualizar.forEach(boton => {
          boton.addEventListener('click', function(){
                editarPaciente(this);
          })
        });
        
    }

    //Funcion para listar
        function listarPacientes(){
            const url = '/pacientes/listar';
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



     /*----------------------------------------ELIMINAR PACIENTE------------------------------------------*/
     function eliminarPaciente(boton){
         console.log(boton.id)

         const url = `/pacientes/eliminar/${boton.id}`
         const settings = {
               method: 'DELETE',
         }
         fetch(url, settings)
             .then( response => response.status )
             .then( data => {
                   console.log(data);
                   // Vuelve a recargar las tareas
                   listarPacientes();
                 })
     }

   


     /*----------------------------------------ACTUALIZAR PACIENTE------------------------------------------*/



    function actualizarPaciente(formData){

      let pacienteActualizado = {
        apellido: formData.apellido,
        nombre: formData.nombre,
        dni: formData.dni,
        fechaAlta: formData.fechaAlta,
        domicilio: formData.domicilio
      }
  
      const url = `/pacientes/actualizar`
      const settings = {
        method: 'PUT',
        headers: {          
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(pacienteActualizado)
      }
      fetch(url, settings)
      .then( response => response.json() )
      .then( data => {
        console.log(data);
        // Vuelve a recargar las tareas
        listarPacientes();
      })
      selectedRow = null;
  }
     
  
  listarPacientes();

})

 
    
   










