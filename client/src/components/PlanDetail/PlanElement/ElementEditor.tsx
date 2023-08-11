import { useRef } from 'react'
import styles from './ElementEditor.module.scss'
import DropDown from './DropDown'

interface ElementEditorViewProps extends React.HTMLProps<HTMLTextAreaElement> {
  textarea: React.RefObject<HTMLTextAreaElement>
  handleResizeHeight: () => void
}

const options = [
  { title: '관광', key: 1 },
  { title: '숙박', key: 2 },
  { title: '교통', key: 3 },
  { title: '카페', key: 4 },
  { title: '식당', key: 5 },
  { title: '기타', key: 6 },
]

function ElementEditorView({ textarea, handleResizeHeight }: ElementEditorViewProps) {
  return (
    <div className={styles.container}>
      <div className={styles.inputBox}>
        <input className={styles.textInput} type='text' placeholder='일정 제목' />
        <DropDown options={options} />
        <input className={styles.timeInput} type='time' placeholder='시간' />
        <input className={styles.textInput} type='text' placeholder='주소' />
        <input className={styles.textInput} type='text' placeholder='지출 금액 (선택 사항)' />
        <textarea
          className={styles.longTextInput}
          rows={1}
          placeholder='세부 내용 (선택 사항)'
          ref={textarea}
          onChange={handleResizeHeight}
        />
      </div>
      <div className={styles.buttons}>
        <div className={styles.addBtn}>추가</div>
        <div className={styles.cancelBtn}>취소</div>
      </div>
    </div>
  )
}
function ElementEditor() {
  const textarea = useRef<HTMLTextAreaElement>(null)
  const handleResizeHeight = () => {
    if (textarea.current) {
      textarea.current.style.height = 'auto'
      textarea.current.style.height = `${textarea.current.scrollHeight}px`
    }
  }

  const props = {
    textarea,
    handleResizeHeight,
  }
  return <ElementEditorView {...props} />
}

export default ElementEditor