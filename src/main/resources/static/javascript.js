const monster = document.getElementById('monster');
const inputUsuario = document.getElementById('input-usuario');
const inputClave = document.getElementById('input-clave');
const body = document.querySelector('body');
const anchoMitad = window.innerWidth / 2;
const altoMitad = window.innerHeight / 2;
let seguirPunteroMouse = true;

// Animaciones del monstruo
body.addEventListener('mousemove', (m) => {
    if (seguirPunteroMouse) {
        if (m.clientX < anchoMitad && m.clientY < altoMitad) {
            monster.src = "/img/idle/2.png";
        } else if (m.clientX < anchoMitad && m.clientY > altoMitad) {
            monster.src = "/img/idle/3.png";
        } else if (m.clientX > anchoMitad && m.clientY < altoMitad) {
            monster.src = "/img/idle/5.png";
        } else {
            monster.src = "/img/idle/4.png";
        }
    }
});

inputUsuario.addEventListener('focus', () => seguirPunteroMouse = false);
inputUsuario.addEventListener('blur', () => seguirPunteroMouse = true);

inputUsuario.addEventListener('keyup', () => {
    let usuario = inputUsuario.value.length;
    if (usuario >= 0 && usuario <= 5) {
        monster.src = '/img/read/1.png';
    } else if (usuario >= 6 && usuario <= 14) {
        monster.src = '/img/read/2.png';
    } else if (usuario >= 15 && usuario <= 20) {
        monster.src = '/img/read/3.png';
    } else {
        monster.src = '/img/read/4.png';
    }
});

inputClave.addEventListener('focus', () => {
    seguirPunteroMouse = false;
    let cont = 1;
    const cubrirOjo = setInterval(() => {
        monster.src = '/img/cover/' + cont + '.png';
        if (cont < 8) {
            cont++;
        } else {
            clearInterval(cubrirOjo);
        }
    }, 60);
});

inputClave.addEventListener('blur', () => {
    seguirPunteroMouse = true;
    let cont = 7;
    const descubrirOjo = setInterval(() => {
        monster.src = '/img/cover/' + cont + '.png';
        if (cont > 1) {
            cont--;
        } else {
            clearInterval(descubrirOjo);
        }
    }, 60);
});

// Evento de login
const formulario = document.querySelector(".formulario");

formulario.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = inputUsuario.value.trim();
    const password = inputClave.value.trim();

    try {
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            const token = data.token;
            localStorage.setItem("token", "Bearer " + token); // para uso interno si es necesario

            await navigator.clipboard.writeText(token); // solo el token, sin "Bearer"
            alert("✅ ¡Login exitoso!\n\nTu token JWT ha sido copiado al portapapeles.\n\nPégalo en Swagger usando el botón 'Authorize'.");

            window.location.href = "http://pruebas.megayuntas.com:1901/swagger-ui/index.html";
        } else {
            alert(`❌ ${data.message || "Credenciales incorrectas"}`);
        }
    } catch (err) {
        console.error("Error en login:", err);
        alert("❌ Error al conectar con el servidor.");
    }
});
