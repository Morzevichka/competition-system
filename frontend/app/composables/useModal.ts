export const useModal = () => {
  const isOpen = useState('modal', () => false)

  const open = () => (isOpen.value = true)
  const close = () => (isOpen.value = false)

  return { isOpen, open, close }
}