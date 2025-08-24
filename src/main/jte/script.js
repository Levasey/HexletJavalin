document.addEventListener('DOMContentLoaded', function() {
    // Автоскрытие flash сообщений
    const flashMessages = document.querySelectorAll('.flash');
    flashMessages.forEach(flash => {
        // Добавляем кнопку закрытия
        const closeBtn = document.createElement('button');
        closeBtn.innerHTML = '&times;';
        closeBtn.className = 'flash-close';
        closeBtn.addEventListener('click', () => flash.remove());
        flash.appendChild(closeBtn);

        // Автоскрытие через 5 секунд
        setTimeout(() => {
            flash.style.opacity = '0';
            flash.style.transition = 'opacity 0.5s ease';
            setTimeout(() => flash.remove(), 500);
        }, 5000);
    });
});