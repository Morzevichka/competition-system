export const useErrorHandler = () => {
    const handle = (e: any): string => {
        // if (e?.status === 401 || e?.status === 403) {
        //     navigateTo("/login");
        // }
        if (e?.status === 400 && Array.isArray(e?.data?.errors)) {
            return e.data.errors
                .map((err: { field: string; message: string }) => err.message)
                .join(';\n');
        }

        return (
            e?.data?.message ||
            e?.message ||
            'Неизвестная ошибка'
        );
    };

    return { handle };
};