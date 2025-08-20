// Обработка форм с method override
document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('form[method="post"]');

    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const methodInput = this.querySelector('input[name="_method"]');
            if (methodInput) {
                e.preventDefault();

                // Создаем новый formData
                const formData = new FormData(this);
                formData.delete('_method');

                // Отправляем fetch запрос с правильным методом
                fetch(this.action, {
                    method: methodInput.value,
                    body: formData,
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                    .then(response => {
                        if (response.redirected) {
                            window.location.href = response.url;
                        } else {
                            location.reload();
                        }
                    })
                    .catch(error => console.error('Error:', error));
            }
        });
    });
});