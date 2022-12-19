window.addEventListener('load', function () {

  const principal = document.getElementById("main");

function cargarUser() {

    const url = '/'
    const settings = {
      method: 'GET',
      headers: {
            'Content-Type': 'application/json',
      }
    }
    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        
     
    })
      .catch(error => {
        console.log(error)        
    })

   }

   cargarUser();


   //renderizo la lista
       function renderizarAdmin(){
           principal.innerHTML = "";
           principal.innerHTML += `<section class="seccion">
                                    <h1>Bienvenido administrador</h1>
                                           <ul>
                                               <li><a href="odontologos.html">Administrar Odontologos</a></li>
                                               <li><a href="pacientes.html">Administrar Pecientes</a></li>
                                               <li> <a href="turnos.html">Administrar Turnos</a></li>
                                           </ul>
                                    </section>`


       }

       function renderizarUser(){
            principa.innerHTML = ""
            principal.innerHTML += "Hola"
       }

})