document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.notice').forEach((notice) => {
        notice.setAttribute('role', 'status');
    });

    document.querySelectorAll('form').forEach((form) => {
        form.style.pointerEvents = 'auto';
    });

    document.querySelectorAll('input, select, textarea, button').forEach((element) => {
        if (element.hasAttribute('disabled')) {
            element.removeAttribute('disabled');
        }

        if (element.hasAttribute('readonly')) {
            element.removeAttribute('readonly');
        }

        element.style.pointerEvents = 'auto';

        if (element.matches('input, textarea')) {
            element.style.userSelect = 'text';
        }
    });

    document.querySelectorAll('[data-confirm]').forEach((element) => {
        element.addEventListener('click', (event) => {
            if (!window.confirm(element.dataset.confirm)) {
                event.preventDefault();
            }
        });
    });
});
