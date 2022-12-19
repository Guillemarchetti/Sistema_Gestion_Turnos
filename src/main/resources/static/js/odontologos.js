window.addEventListener('load', function () {

    //Variables globales
    const tablaBody = document.getElementById("tablaOdontologosBody");  
    const formOdontologo = document.querySelector(".formulario");
    const inputNombre = document.getElementById("inputName");
    const inputApellido = document.getElementById("inputApellido");
    const inputMatricula = document.getElementById("inputMatricula");


    //Boton de editar
    function editarOdontologo(o){
        selectedRow = o.parentElement.parentElement       
        inputNombre.value = selectedRow.cells[0].innerHTML;
        inputApellido.value = selectedRow.cells[1].innerHTML;
        inputMatricula.value = selectedRow.cells[2].innerHTML;
    }


    //Leer los datos del form
    function leerForm(){
      let formData={};
      formData['apellido'] = inputApellido.value;
      formData['nombre'] = inputNombre.value;
      formData['matricula'] = inputMatricula.value;
      return formData;
    }

    //Resetear el form
    function resetearForm(){
      inputNombre.value = '';
      inputApellido.value = '';
      inputMatricula.value = '';
    }


    //Submiteo el form
    let selectedRow= null;
    formOdontologo.addEventListener('submit', function (event) {
       console.log("selectedRow: ", selectedRow)
       event.preventDefault();
       let formData = leerForm();       
       if(selectedRow === null) {
            crearOdontologo();
        } else{
            actualizarOdontologo(formData);
        }
        resetearForm();
    })
    



/*----------------------------------------CREAR ODONTOLOGO------------------------------------------*/

   function crearOdontologo() {       
                
    let odontologo = {
      apellido: inputApellido.value,
      nombre: inputNombre.value,
      matricula: inputMatricula.value
    }         

    const url = '/odontologos/crear';
    const settings = {
      method: 'POST',
      headers: {
            'Content-Type': 'application/json',
      },
      body: JSON.stringify(odontologo)
    }

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        window.alert("Odontologo creado con exito")
        listarOdontologos();
    })
      .catch(error => {
        console.log(error)
        window.alert("No se pudo crear el odontologo")
    })       

   }

    /*----------------------------------------LISTAR ODONTOLOGOS------------------------------------------*/

    //renderizo la lista
    function renderizarLista(odontologos){ 
        tablaBody.innerHTML = "";
        odontologos.forEach(odontologo => {
            tablaBody.innerHTML += `<tr>
                                        <td id="nombre">${odontologo.nombre}</td>
                                        <td id="apellido">${odontologo.apellido}</td>
                                        <td id="matricula">${odontologo.matricula}</td>
                                        <td id="botones"> 
                                            <button id=${odontologo.matricula} class="actualizar">Actualizar</button>
                                            <button id=${odontologo.matricula} class="borrar">Eliminar</button>
                                        </td>
                                     </tr>`
        })

        
        const botonesBorrar = document.querySelectorAll('.borrar');
        const botonesActualizar = document.querySelectorAll('.actualizar')
        

        botonesBorrar.forEach(boton => {
          boton.addEventListener('click', function(event){
                eliminarOdontologo(event.target);
          })
        });

        botonesActualizar.forEach(boton => {
          boton.addEventListener('click', function(){
                editarOdontologo(this);
          })
        });
        
    }

    //Funcion para listar
        function listarOdontologos(){
            const url = '/odontologos/listar';
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



     /*----------------------------------------ELIMINAR ODONTOLOGO------------------------------------------*/
     function eliminarOdontologo(boton){
        console.log(boton.id) //Me trae la matricula



         const url = `/odontologos/eliminar/${boton.id}`
         const settings = {
               method: 'DELETE',
         }
         fetch(url, settings)
             .then( response => response.status )
             .then( data => {
                   console.log(data);
                   // Vuelve a recargar las tareas
                   listarOdontologos();
                 })
     }

   


     /*----------------------------------------ACTUALIZAR ODONTOLOGO------------------------------------------*/



    function actualizarOdontologo(formData){

      let odontologoActualizado = {
        apellido: formData.apellido,
        nombre: formData.nombre,
        matricula: formData.matricula
      }
  
      const url = `/odontologos/actualizar`
      const settings = {
        method: 'PUT',
        headers: {          
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(odontologoActualizado)
      }
      fetch(url, settings)
      .then( response => response.json() )
      .then( data => {
        console.log(data);
        // Vuelve a recargar las tareas
        listarOdontologos();
      })
      selectedRow = null;
  }
     
  
  listarOdontologos();

})

 
    
   










