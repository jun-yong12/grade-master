document.addEventListener('DOMContentLoaded', () => {
    // 알림 메시지 3초 후 자동 숨김
    document.querySelectorAll('.alert').forEach(el => {
        setTimeout(() => {
            el.style.transition = 'opacity 0.5s';
            el.style.opacity = '0';
            setTimeout(() => el.remove(), 500);
        }, 3000);
    });
});
