document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.notice').forEach((notice) => {
        notice.setAttribute('role', 'status');
    });

    document.querySelectorAll('[data-confirm]').forEach((element) => {
        element.addEventListener('click', (event) => {
            if (!window.confirm(element.dataset.confirm)) {
                event.preventDefault();
            }
        });
    });
});
